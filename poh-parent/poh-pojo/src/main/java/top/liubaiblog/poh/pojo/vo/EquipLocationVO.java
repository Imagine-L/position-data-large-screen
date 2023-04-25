package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 留白
 * @description 设备定位视图对象
 */
@Data
public class EquipLocationVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long queryUid;                // 用户id
    private String equipCode;               // 设备码
    private boolean main;                   // 是否为主设备
    private String longitude;               // 经度
    private String latitude;                // 纬度
    private String longitudeHemisphere;     // 经度半球
    private String latitudeHemisphere;      // 纬度半球
}
