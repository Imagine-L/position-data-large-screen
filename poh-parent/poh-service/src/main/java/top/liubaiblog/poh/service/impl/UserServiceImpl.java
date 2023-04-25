package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.liubaiblog.baselutils.spring.util.jwt.JwtUtils;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.poh.common.exception.RemoveOwnException;
import top.liubaiblog.poh.mapper.UserMapper;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.FamilyParams;
import top.liubaiblog.poh.pojo.param.NewFamilyParams;
import top.liubaiblog.poh.pojo.param.UpdatePwdParams;
import top.liubaiblog.poh.pojo.param.UserParams;
import top.liubaiblog.poh.pojo.po.Menu;
import top.liubaiblog.poh.pojo.po.User;
import top.liubaiblog.poh.pojo.vo.UserDetailVo;
import top.liubaiblog.poh.pojo.vo.UserVo;
import top.liubaiblog.poh.security.pass.Md5PasswordEncoder;
import top.liubaiblog.poh.service.IMenuService;
import top.liubaiblog.poh.service.IUserConnectRoleService;
import top.liubaiblog.poh.service.IUserService;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Autowired
    private IUserConnectRoleService userConnectRoleService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public boolean isUsernameExist(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return count(wrapper) != 0;
    }

    @Override
    public LoginUserDTO updateLoginUser() {
        // 获取登录的用户
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 从数据库更新登录的用户信息
        User user = getById(loginUser.getUid());
        BeanUtils.copyProperties(user, loginUser);
        Long parentUid = user.getParentUid();
        loginUser.setQueryUid(parentUid == null || parentUid <= 0 ? loginUser.getUid() : parentUid);
        loginUser.setGender(Objects.equals(user.getGender(), 0) ? "男" : "女");
        String token = loginUser.getToken();
        // 重新获取当前用户权限
        List<Menu> newMenus = menuService.listByUser(loginUser.getUid());
        List<String> newPermissionList = newMenus.stream().map(Menu::getPermission).collect(Collectors.toList());
        boolean isToBackstage = newPermissionList.containsAll(SecureConstants.TO_BACKSTAGE_PERMISSIONS);
        loginUser.setToBackstage(isToBackstage);

        // 判断是否需要更新token
        Long minute = redisUtils.getExpire(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token, TimeUnit.MINUTES);
        if (minute <= 30) {
            HashMap<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("uid", loginUser.getUid());
            tokenMap.put("username", loginUser.getUsername());
            token = JwtUtils.create(tokenMap);
            loginUser.setToken(token);
        }
        // 更新token信息
        redisUtils.valSet(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token,
                loginUser,
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        redisUtils.valSet(SecureConstants.REDIS_PERMISSION_PREFIX + token,
                newPermissionList,
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        return loginUser;
    }

    @Override
    public boolean updateByParams(UserParams userParams) {
        // 参数校验
        if (userParams == null || !StringUtils.hasLength(userParams.getNickname())) {
            return false;
        }
        // 封装参数
        LoginUserDTO loginUser = UserThreadLocal.get();
        User user = new User();
        user.setUid(loginUser.getUid());
        user.setNickname(userParams.getNickname());
        user.setEmail(userParams.getEmail());
        Integer genderCode = userParams.getGender();
        user.setGender(genderCode == 0 || genderCode == 1 ? genderCode : 0);
        // 更新数据
        return updateById(user);
    }

    @Override
    public boolean updatePassword(UpdatePwdParams updatePwdParams) {
        // 参数校验
        if (updatePwdParams == null ||
                !StringUtils.hasLength(updatePwdParams.getOldPwd()) ||
                !StringUtils.hasLength(updatePwdParams.getNewPwd())) {
            return false;
        }
        // 判断新旧密码是否一致，一致则无需修改，
        if (Objects.equals(updatePwdParams.getNewPwd(), updatePwdParams.getOldPwd())) {
            return false;
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        String encodeNewPwd = passwordEncoder.encode(updatePwdParams.getNewPwd());
        String encodeOldPwd = passwordEncoder.encode(updatePwdParams.getOldPwd());
        // 比对原密码
        LambdaQueryWrapper<User> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(User::getUid, loginUser.getUid());
        wrapper1.eq(User::getPassword, encodeOldPwd);
        long count = count(wrapper1);
        if (count <= 0) {
            return false;
        }
        // 封装参数修改密码
        LambdaUpdateWrapper<User> wrapper2 = new LambdaUpdateWrapper<>();
        wrapper2.eq(User::getUid, loginUser.getUid());
        wrapper2.set(User::getPassword, encodeNewPwd);
        // 更新数据表
        return update(wrapper2);
    }

    @Override
    public List<UserVo> listFamilyByParams(FamilyParams familyParams) {
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 封装查询参数
        wrapper.eq(User::getParentUid, loginUser.getQueryUid());
        if (familyParams != null) {
            if (StringUtils.hasLength(familyParams.getUsername())) {
                wrapper.like(User::getUsername, familyParams.getUsername());
            }
            if (StringUtils.hasLength(familyParams.getNickname())) {
                wrapper.like(User::getNickname, familyParams.getNickname());
            }
            if (StringUtils.hasLength(familyParams.getIdentity())) {
                wrapper.like(User::getIdentity, familyParams.getIdentity());
            }
        }
        wrapper.select(User::getUsername, User::getNickname, User::getIdentity, User::getUid);
        // 查询数据
        List<User> families = list(wrapper);
        // 转化成视图层数据返回
        return families.stream().map(family -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(family, userVo);
            return userVo;
        }).collect(Collectors.toList());
    }

    @Override
    public UserDetailVo getFamilyDetailById(Long uid) {
        // 参数校验
        if (uid == null) return new UserDetailVo();
        // 封装条件并查询
        LoginUserDTO loginUser = UserThreadLocal.get();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getParentUid, loginUser.getQueryUid());
        wrapper.eq(User::getUid, uid);
        User user = getOne(wrapper);
        // 封装视图对象
        UserDetailVo userDetailVo = new UserDetailVo();
        BeanUtils.copyProperties(user, userDetailVo);
        userDetailVo.setLocked(Objects.equals(user.getLocked(), 1));
        userDetailVo.setGender(Objects.equals(user.getGender(), 0) ? "男" : "女");
        // 用户是否具有控制后台的权限
        boolean userHasRole = userConnectRoleService.isUserHasRole(user.getUid(), SecureConstants.SUPER_FAMILY_ID);
        userDetailVo.setControlBackstage(userHasRole);
        return userDetailVo;
    }

    @Override
    public boolean updateFamilyByParams(NewFamilyParams familyParams) {
        // 参数校验
        if (familyParams == null || familyParams.getUid() == null) {
            return false;
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 封装更新参数
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getParentUid, loginUser.getQueryUid());
        wrapper.eq(User::getUid, familyParams.getUid());
        User user = new User();
        user.setNickname(familyParams.getNickname());
        user.setIdentity(familyParams.getIdentity());
        String password = familyParams.getPassword();
        if (StringUtils.hasLength(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        Integer genderCode = familyParams.getGender();
        user.setGender(genderCode == 0 || genderCode == 1 ? genderCode : 0);
        user.setLocked(familyParams.isLocked() ? 1 : 0);
        // 更新
        boolean result = update(user, wrapper);
        if (result) {
            taskExecutor.execute(() -> {
                // 是否需要更新控制后台的权限[异步]
                if (familyParams.isControlBackstage()) {
                    // 是则添加管理后台的家人角色
                    userConnectRoleService.grantUserById(familyParams.getUid(),
                            SecureConstants.SUPER_FAMILY_ID,
                            loginUser.getUid());
                } else {
                    // 否则取消该角色的后台家人角色
                    userConnectRoleService.revokeById(familyParams.getUid(), SecureConstants.SUPER_FAMILY_ID);
                }
            });
        }
        return result;
    }

    @Override
    public boolean removeFamilyById(Long uid) {
        if (uid == null) return false;
        // 禁止用户自己删除自己
        LoginUserDTO loginUser = UserThreadLocal.get();
        if (Objects.equals(uid, loginUser.getUid())) {
            throw new RemoveOwnException("用户删除自己");
        }
        // 删除用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getParentUid, loginUser.getQueryUid());
        wrapper.eq(User::getUid, uid);
        boolean result = remove(wrapper);
        // 删除用户对应的权限
        if (result) {
            taskExecutor.execute(() -> {
                userConnectRoleService.revokeById(uid);
            });
        }
        return result;
    }

    @Override
    public boolean saveFamilyByParams(NewFamilyParams familyParams) {
        // 参数校验
        if (familyParams == null ||
                !StringUtils.hasLength(familyParams.getUsername()) ||
                !StringUtils.hasLength(familyParams.getPassword())) {
            return false;
        }
        // 判断用户名是否重复
        if (isUsernameExist(familyParams.getUsername())) {
            return false;
        }
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 封装参数
        User user = new User();
        user.setUsername(familyParams.getUsername());
        user.setPassword(passwordEncoder.encode(familyParams.getPassword()));
        user.setNickname(familyParams.getNickname());
        user.setIdentity(familyParams.getIdentity());
        user.setLocked(familyParams.isLocked() ? 1 : 0);
        user.setGender(familyParams.getGender());
        user.setParentUid(loginUser.getQueryUid());
        user.setCreateBy(loginUser.getUid());
        user.setCreateTime(new Date());
        // 保存对象
        boolean result = save(user);
        // 是否需要更新控制后台的权限[异步]
        if (result && familyParams.isControlBackstage()) {
            taskExecutor.execute(() -> {
                userConnectRoleService.grantUserById(user.getUid(),
                        SecureConstants.SUPER_FAMILY_ID,
                        loginUser.getUid());
            });
        }
        return result;
    }

}

/*
    @Override
    public LoginUserDTO updateLoginUser() {
        // 获取登录的用户
        LoginUserDTO loginUser = UserThreadLocal.get();
        // 从数据库更新登录的用户信息
        User user = getById(loginUser.getUid());
        BeanUtils.copyProperties(user, loginUser);
        Long parentUid = user.getParentUid();
        loginUser.setQueryUid(parentUid == null || parentUid <= 0 ? loginUser.getUid() : parentUid);
        loginUser.setGender(Objects.equals(user.getGender(), 0) ? "男" : "女");
        String oldToken = loginUser.getToken();
        // 判断是否需要更新token
        Long minute = redisUtils.getExpire(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + oldToken, TimeUnit.MINUTES);
        if (minute <= 30) {

        }
        HashMap<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("uid", loginUser.getUid());
        tokenMap.put("username", loginUser.getUsername());
        String newToken = JwtUtils.create(tokenMap);
        loginUser.setToken(newToken);
        // ...
        // 重新获取当前用户权限
        List<Menu> newMenus = menuService.listByUser(loginUser.getUid());
        List<String> newPermissionList = newMenus.stream().map(Menu::getPermission).collect(Collectors.toList());
        boolean isToBackstage = newPermissionList.containsAll(SecureConstants.TO_BACKSTAGE_PERMISSIONS);
        loginUser.setToBackstage(isToBackstage);
        // 删除旧的token，并设置新的
         redisUtils.delete(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + oldToken);
         redisUtils.delete(SecureConstants.REDIS_PERMISSION_PREFIX + oldToken);
         // .....
        redisUtils.valSet(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + newToken,
                loginUser,
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        redisUtils.valSet(SecureConstants.REDIS_PERMISSION_PREFIX + newToken,
                newPermissionList,
                SecureConstants.REDIS_EXPIRATION_SECONDS,
                TimeUnit.SECONDS);
        return loginUser;
    }
*/