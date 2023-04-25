package top.liubaiblog.poh.mapper;

import org.apache.ibatis.annotations.Param;
import top.liubaiblog.poh.pojo.po.BoughtEquipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
public interface BoughtEquipmentMapper extends BaseMapper<BoughtEquipment> {

    /**
     * 将指定id的设备的设备组修改为指定组
     */
    Integer updateGroupIdById(@Param("ids") List<Long> ids, @Param("groupId") Long groupId);

    /**
     * 将指定分组的所有设备都展示
     */
    Integer updateShowedByGroupId(@Param("groupId") Long groupId, @Param("isShowed") boolean isShowed);

    /**
     * 将指定分组的所有设备都禁用
     */
    Integer updateLockedByGroupId(@Param("groupId") Long groupId, @Param("isLocked") boolean isLocked);

}
