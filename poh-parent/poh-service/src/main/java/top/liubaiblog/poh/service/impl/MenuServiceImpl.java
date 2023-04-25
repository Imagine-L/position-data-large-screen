package top.liubaiblog.poh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.baselutils.util.http.HttpRequestUtils;
import top.liubaiblog.poh.mapper.MenuMapper;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.pojo.po.Menu;
import top.liubaiblog.poh.service.IMenuService;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Menu> listByUser(Long uid) {
        List<Menu> menus = getBaseMapper().selectListByUser(uid);
        return menus == null ? Collections.emptyList() : menus;
    }

    @Override
    public boolean hasAllPermissions(List<String> permissions) {
        // 从缓存中获取用户的所有权限
        String token = HttpRequestUtils.getToken();
        List<String> userPermissions = redisUtils.valGetObject(SecureConstants.REDIS_PERMISSION_PREFIX + token, List.class);
        // 判断用户是否有对应的全部权限
        return userPermissions.containsAll(permissions);
    }

    @Override
    public List<Menu> getByRoleId(Long rid) {
        List<Menu> menus = getBaseMapper().getByRoleId(rid);
        return menus == null ? Collections.emptyList() : menus;
    }
}
