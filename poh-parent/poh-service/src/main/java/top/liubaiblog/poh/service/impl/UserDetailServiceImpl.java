package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.liubaiblog.poh.pojo.dto.SecurityUserDTO;
import top.liubaiblog.poh.pojo.po.Menu;
import top.liubaiblog.poh.pojo.po.User;
import top.liubaiblog.poh.service.IMenuService;
import top.liubaiblog.poh.service.IUserService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 留白
 * @description
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IMenuService menuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 参数校验
        if (!StringUtils.hasLength(username)) {
            throw new UsernameNotFoundException("用户名参数为空");
        }
        // 根据用户名获取用户
        User currentUser = userService.getByUsername(username);
        if (currentUser == null) {
            throw new UsernameNotFoundException("用户名未找到");
        }
        if (Objects.equals(currentUser.getLocked(), 1)) {
            throw new AuthorizationServiceException("用户被锁定");
        }
        // 根据用户名查询权限列表
        List<Menu> menus = menuService.listByUser(currentUser.getUid());
        List<String> permissionStringList = menus.stream().map(Menu::getPermission).collect(Collectors.toList());
        // 封装对象并返回
        return new SecurityUserDTO(currentUser, permissionStringList);
    }

}
