package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 留白
 * @description
 */
@Data
public class EquipmentDetailVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long eid;               // 设备id
    private String equipName;   // 设备名
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;  // 设备组
    private String equipCode;   // 设备码
    private String version;         // 设备版本
    private String type;            // 设备型号
    private Integer batteryCapacity;    // 电池容量
    private boolean showed;             // 是否展示
    private boolean main;               // 是否为主设备
    private boolean locked;             // 设备是否锁定
}
