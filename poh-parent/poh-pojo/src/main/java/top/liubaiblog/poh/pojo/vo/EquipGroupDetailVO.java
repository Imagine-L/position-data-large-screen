package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 留白
 * @description 设备分组的详细信息
 */
@Data
public class EquipGroupDetailVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gid;                       // 分组ID
    private String groupName;               // 分组名
    private String description;             // 分组描述
    private List<EquipmentVO> equipments;   // 该分组下的所有设备
    private int count;                      // 该分组下的设备数量
    private Date createTime;                // 该分组的创建时间
    private boolean equipmentsShowed;                 // 该分组的设备是否展示
    private boolean equipmentsLocked;                 // 该分组的所有设备是否锁定
}
