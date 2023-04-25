package top.liubaiblog.poh.mapper;

import top.liubaiblog.poh.pojo.po.Equipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {

    /**
     * 指定设备码在设备表中是否存在，并且该设备码为被使用
     */
    Integer isCodeExistAndNotUse(String equipCode);

}
