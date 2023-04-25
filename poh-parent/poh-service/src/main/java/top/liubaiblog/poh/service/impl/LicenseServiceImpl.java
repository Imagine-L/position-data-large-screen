package top.liubaiblog.poh.service.impl;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import top.liubaiblog.baselutils.spring.util.jwt.JwtUtils;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.baselutils.util.http.HttpRequestUtils;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.exception.RegisterMessageException;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.dto.SecurityUserDTO;
import top.liubaiblog.poh.pojo.param.LicenseParams;
import top.liubaiblog.poh.pojo.po.*;
import top.liubaiblog.poh.service.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserConnectRoleService userConnectRoleService;

    @Autowired
    private IBoughtEquipmentService boughtEquipmentService;

    @Autowired
    private IEquipmentService equipmentService;

    @Autowired
    private IEquipGroupService equipGroupService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    @SuppressWarnings("all")
    private AmqpAdmin amqpAdmin;

    @Autowired
    @Qualifier("userExchange")
    private DirectExchange userExchange;

    @Override
    public LoginUserDTO loginGetLoginUser(LicenseParams licenseParams) {
        // 参数校验
        if (!checkParams(licenseParams, false, false)) {
            throw new AuthenticationServiceException("用户名或密码为空");
        }

        // 封装用户参数
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(licenseParams.getUsername(),
                licenseParams.getPassword(),
                Collections.emptyList());
        Authentication authenticate = null;
        try {
            // 使用authenticationManager对参数进行验证
            authenticate = authenticationManager.authenticate(authenticationToken);
            if (authenticate == null) {
                unsuccessful(new AuthenticationServiceException("用户名或密码错误"));
                return null;
            }
            // 进行认证成功的处理
            return successful(authenticate);
        } catch (AuthenticationException e) {
            unsuccessful(e);
            return null;
        }
    }

    @Override
    public void logout() {
        log.info("用户登出操作");
        String token = HttpRequestUtils.getToken();
        redisUtils.delete(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token);
        redisUtils.delete(SecureConstants.REDIS_PERMISSION_PREFIX + token);
    }

    @Override
    public LoginUserDTO registerGetLoginUser(LicenseParams licenseParams) {
        // 参数校验
        if (!checkParams(licenseParams, true, true)) {
            throw new RegisterMessageException("请求参数异常");
        }
        // 创建新用户和角色
        User newUser = newUserAndRole(licenseParams);
        // 异步创建设备机器分组
        taskExecutor.execute(() -> {
            // 创建新的设备以及分组
            newEquipmentAndGroup(licenseParams, newUser);
            // 创建消息队列，并绑定到用户交换机中
            Queue newQueue = QueueBuilder
                    .nonDurable(IotClientConstants.USER_QUEUE_PREFIX + newUser.getUid())
                    .build();
            amqpAdmin.declareQueue(newQueue);
            Binding newBinding = BindingBuilder.bind(newQueue)
                    .to(userExchange)
                    .with(String.valueOf(newUser.getUid()));
            amqpAdmin.declareBinding(newBinding);
        });
        // 封装安全认证信息
        List<Menu> menus = menuService.listByUser(newUser.getUid());
        List<String> permissionStringList = menus.stream().map(Menu::getPermission).collect(Collectors.toList());
        SecurityUserDTO securityUser = new SecurityUserDTO(newUser, permissionStringList);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(securityUser,
                licenseParams.getPassword(),
                securityUser.getAuthorities());
        return successful(authenticationToken);
    }

    /**
     * 认证成功
     */
    private LoginUserDTO successful(Authentication authenticate) {
        HttpServletRequest request = HttpRequestUtils.getLocalRequest();
        // 认证成功，密码比对一致，则获取认证成功之后的用户信息
        SecurityUserDTO securityUser = (SecurityUserDTO) authenticate.getPrincipal();
        if (securityUser == null || securityUser.getCurrentUser() == null) {
            throw new AuthenticationServiceException("current login user is null");
        }

        // 生成token
        HashMap<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("uid", securityUser.getCurrentUser().getUid());
        tokenMap.put("username", securityUser.getUsername());
        String token = JwtUtils.create(tokenMap);

        // 封装登录的用户
        LoginUserDTO loginUser = new LoginUserDTO();
        BeanUtils.copyProperties(securityUser.getCurrentUser(), loginUser);
        Long parentUid = securityUser.getCurrentUser().getParentUid();
        loginUser.setQueryUid(parentUid == null || parentUid <= 0 ? loginUser.getUid() : parentUid);
        loginUser.setGender(Objects.equals(securityUser.getCurrentUser().getGender(), 0) ? "男" : "女");
        loginUser.setToken(token);
        loginUser.setRemoteIp(request.getRemoteAddr());
        boolean isToBackstage = securityUser.getPermissionStringList().containsAll(SecureConstants.TO_BACKSTAGE_PERMISSIONS);
        loginUser.setToBackstage(isToBackstage);
        UserAgent userAgent = HttpRequestUtils.getUserAgent();
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setSystem(userAgent.getOperatingSystem().getName());

        // 将token和登录的信息注入到redis
        redisUtils.valSet(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token,
                loginUser,
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        redisUtils.valSet(SecureConstants.REDIS_PERMISSION_PREFIX + token,
                securityUser.getPermissionStringList(),
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        // 将登录的用户返回给前端
        return loginUser;
    }

    /**
     * 认证失败，暂无具体具体实现，后续可以补充
     */
    private void unsuccessful(Exception e) {
        log.warn("认证失败 -> {}", e.getMessage());
    }

    /**
     * 参数校验
     */
    private boolean checkParams(LicenseParams licenseParams, boolean isCheckUserExist, boolean isCheckEquipCode) {
        // 用户名和密码的非空校验
        if (licenseParams == null ||
                !StringUtils.hasLength(licenseParams.getUsername()) ||
                !StringUtils.hasLength(licenseParams.getPassword())) {
            return false;
        }
        // 检查用户名是否已存在
        if (isCheckUserExist) {
            boolean isExist = userService.isUsernameExist(licenseParams.getUsername());
            if (isExist) {
                return false;
            }
        }
        // 校验设备码
        if (isCheckEquipCode) {
            // 确保设备码有长度，并且设备码存在且未被使用
            return StringUtils.hasLength(licenseParams.getEquipCode()) &&
                    equipmentService.isCodeExistAndNotUse(licenseParams.getEquipCode());
        }
        return true;
    }


    /**
     * 创建新用户和角色
     */
    private User newUserAndRole(LicenseParams licenseParams) {
        // 封装用户对象
        User user = new User();
        user.setUsername(licenseParams.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(String.valueOf(licenseParams.getPassword()).getBytes(StandardCharsets.UTF_8)));
        user.setNickname(licenseParams.getUsername());
        user.setIdentity("系统用户");
        user.setGender(0);
        user.setLocked(0);
        user.setCreateBy(1L);
        user.setCreateTime(new Date());
        // 保存用户对象
        boolean saveResult = userService.save(user);
        if (!saveResult) {
            throw new RegisterMessageException("创建用户失败");
        }
        // 关联用户和角色，默认新用户是系统用户
        UserConnectRole userConnectRole = new UserConnectRole();
        userConnectRole.setUid(user.getUid());
        userConnectRole.setRid(SecureConstants.SYSTEM_USER_ID);
        userConnectRole.setCreateBy(0L);
        userConnectRole.setCreateTime(new Date());
        boolean connectResult = userConnectRoleService.save(userConnectRole);
        if (!connectResult) {
            throw new RegisterMessageException("用户与角色关联失败");
        }
        return user;
    }

    /**
     * 创建新的设备和分组
     */
    private void newEquipmentAndGroup(LicenseParams licenseParams, User owner) {
        // 创建用户的默认分组
        EquipGroup equipGroup = new EquipGroup();
        equipGroup.setGroupName("默认分组");
        equipGroup.setDescription("系统提供的默认分组");
        equipGroup.setGroupOwner(owner.getUid());
        equipGroup.setCreateBy(owner.getUid());
        equipGroup.setCreateTime(new Date());
        boolean groupResult = equipGroupService.save(equipGroup);
        if (!groupResult) {
            throw new RegisterMessageException("分组创建失败");
        }

        // 创建新设备并关联分组
        BoughtEquipment boughtEquipment = new BoughtEquipment();
        boughtEquipment.setEquipName("默认设备");
        boughtEquipment.setEquipCode(licenseParams.getEquipCode());
        boughtEquipment.setEquipOwner(owner.getUid());
        boughtEquipment.setGroupId(equipGroup.getGid());
        boughtEquipment.setDanger(false);
        boughtEquipment.setShowed(true);
        boughtEquipment.setMain(true);
        boughtEquipment.setLocked(false);
        boughtEquipment.setCreateBy(owner.getUid());
        boughtEquipment.setCreateTime(new Date());
        boolean equipmentResult = boughtEquipmentService.save(boughtEquipment);
        if (!equipmentResult) {
            throw new RegisterMessageException("设备创建失败");
        }
    }
}
