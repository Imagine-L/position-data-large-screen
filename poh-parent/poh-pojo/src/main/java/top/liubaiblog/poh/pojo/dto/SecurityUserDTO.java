package top.liubaiblog.poh.pojo.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.liubaiblog.poh.pojo.po.Menu;
import top.liubaiblog.poh.pojo.po.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 留白
 * @description 安全认证用户
 */
@Data
public class SecurityUserDTO implements UserDetails {

    // 当前登录的用户
    private transient User currentUser;

    // 当前用户的权限(字符串)
    private List<String> permissionStringList;

    // 当前用户的权限(GrantedAuthority对象)
    private volatile List<GrantedAuthority> authorities;

    public SecurityUserDTO(User currentUser, List<String> permissionStringList) {
        if (currentUser != null && permissionStringList != null) {
            this.currentUser = currentUser;
            this.permissionStringList = permissionStringList;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 如果权限列表为空，则需要进行转化，不然可以直接返回
        if (authorities == null) {
            // 双重检查锁，保证线程安全
            synchronized (this) {
                if (authorities == null) {
                    authorities = permissionStringList.stream()
                            .map(permission -> (GrantedAuthority) () -> permission)
                            .collect(Collectors.toList());
                }
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return currentUser != null ? currentUser.getPassword() : null;
    }

    @Override
    public String getUsername() {
        return currentUser != null ? currentUser.getUsername() : null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return currentUser != null && Objects.equals(currentUser.getLocked(), 0);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
