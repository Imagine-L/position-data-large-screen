package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 根据用户Id获取权限
     */
    List<Menu> listByUser(Long uid);

    /**
     * 用户是否有参数中菜单中的权限
     */
    boolean hasAllPermissions(List<String> permissions);

    /**
     * 根据角色Id获取角色的权限
     */
    List<Menu> getByRoleId(Long rid);
}
