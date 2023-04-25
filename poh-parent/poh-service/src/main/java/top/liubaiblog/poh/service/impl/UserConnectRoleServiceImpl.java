package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.liubaiblog.poh.mapper.UserConnectRoleMapper;
import top.liubaiblog.poh.pojo.po.UserConnectRole;
import top.liubaiblog.poh.service.IUserConnectRoleService;

import java.util.Date;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
@Transactional
public class UserConnectRoleServiceImpl extends ServiceImpl<UserConnectRoleMapper, UserConnectRole> implements IUserConnectRoleService {

    @Override
    public boolean isUserHasRole(Long uid, Long rid) {
        LambdaQueryWrapper<UserConnectRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserConnectRole::getUid, uid);
        wrapper.eq(UserConnectRole::getRid, rid);
        return count(wrapper) > 0;
    }

    @Override
    public boolean grantUserById(Long uid, Long rid, Long createBy) {
        // 如果用户已经有该权限则直接返回true
        if (isUserHasRole(uid, rid)) {
            return true;
        }
        UserConnectRole userConnectRole = new UserConnectRole();
        userConnectRole.setUid(uid);
        userConnectRole.setRid(rid);
        userConnectRole.setCreateBy(createBy);
        userConnectRole.setCreateTime(new Date());
        return save(userConnectRole);
    }

    @Override
    public boolean revokeById(Long uid, Long rid) {
        LambdaQueryWrapper<UserConnectRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserConnectRole::getUid, uid);
        wrapper.eq(UserConnectRole::getRid, rid);
        return remove(wrapper);
    }

    @Override
    public boolean revokeById(Long uid) {
        LambdaQueryWrapper<UserConnectRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserConnectRole::getUid, uid);
        return remove(wrapper);
    }

}
