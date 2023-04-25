package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.liubaiblog.poh.mapper.RoleConnectMenuMapper;
import top.liubaiblog.poh.pojo.po.RoleConnectMenu;
import top.liubaiblog.poh.pojo.po.UserConnectRole;
import top.liubaiblog.poh.service.IRoleConnectMenuService;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class RoleConnectMenuServiceImpl extends ServiceImpl<RoleConnectMenuMapper, RoleConnectMenu> implements IRoleConnectMenuService {

}
