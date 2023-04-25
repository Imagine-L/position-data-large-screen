package top.liubaiblog.poh.service;

import top.liubaiblog.poh.pojo.po.UserConnectRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface IUserConnectRoleService extends IService<UserConnectRole> {

    /**
     * 用户是否具有某个角色
     */
    boolean isUserHasRole(Long uid, Long rid);

    /**
     * 授予指定用户指定角色
     */
    boolean grantUserById(Long uid, Long rid, Long createBy);

    /**
     * 取消指定用户的指定角色
     */
    boolean revokeById(Long uid, Long rid);

    /**
     * 删除指定用户的全部角色
     */
    boolean revokeById(Long uid);

}
