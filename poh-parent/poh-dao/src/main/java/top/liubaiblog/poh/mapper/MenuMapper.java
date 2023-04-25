package top.liubaiblog.poh.mapper;

import org.apache.ibatis.annotations.Select;
import top.liubaiblog.poh.pojo.po.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id获取权限列表
     */
    List<Menu> selectListByUser(Long uid);

    /**
     * 根据角色id获取权限列表
     */
    List<Menu> getByRoleId(Long rid);
}
