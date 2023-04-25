package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.liubaiblog.poh.mapper.RoleMapper;
import top.liubaiblog.poh.pojo.po.Role;
import top.liubaiblog.poh.pojo.po.UserConnectRole;
import top.liubaiblog.poh.service.IRoleService;
import top.liubaiblog.poh.service.IUserConnectRoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
