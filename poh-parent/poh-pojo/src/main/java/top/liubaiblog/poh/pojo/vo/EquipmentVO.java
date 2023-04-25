package top.liubaiblog.poh.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 留白
 * @description 设备的视图对象，仅用于设备列表栏的展示
 */
@Data
public class EquipmentVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long eid;               // 设备id
    private String equipName;   // 设备名
    private String groupName;  // 设备组
    private String equipCode;   // 设备码
    private boolean locked;      // 设备是否锁定
    private boolean showed;      // 设备是否展示
}
