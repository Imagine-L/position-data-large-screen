package top.liubaiblog.poh.mapper;

import top.liubaiblog.poh.pojo.param.EquipGroupParams;
import top.liubaiblog.poh.pojo.po.EquipGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.liubaiblog.poh.pojo.vo.EquipGroupVO;

import java.util.List;

/**
 * <p>
 * 设备分组表 Mapper 接口
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface EquipGroupMapper extends BaseMapper<EquipGroup> {

    /**
     * 根据用户id获取某个用户的所有设备分组名字列表
     */
    List<String> getNamesByUid(Long uid);

    /**
     * 根据请求参数获取用户的分组列表
     */
    List<EquipGroupVO> listByParams(EquipGroupParams groupParams);
}
