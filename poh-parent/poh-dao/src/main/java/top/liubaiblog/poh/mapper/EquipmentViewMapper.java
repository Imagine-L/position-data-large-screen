package top.liubaiblog.poh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.liubaiblog.poh.pojo.param.EquipmentParams;
import top.liubaiblog.poh.pojo.po.EquipmentView;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;

import java.util.List;

/**
* @author 13326
* @description 针对表【poh_equipment_view】的数据库操作Mapper
* @createDate 2022-06-21 19:59:22
* @Entity top.liubaiblog.poh.pojo.EquipmentView
*/
public interface EquipmentViewMapper extends BaseMapper<EquipmentView> {

    /**
     * 根据参数获取设备列表
     */
    List<EquipmentVO> listByParams(EquipmentParams equipmentParams);

}




