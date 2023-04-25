package top.liubaiblog.poh.common.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description 设备定位的经纬度
 */
@Data
public class EquipLocationMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String equipCode;               // 设备码
    private String longitude;               // 经度
    private String latitude;                // 纬度
    private String longitudeHemisphere;     // 经度半球
    private String latitudeHemisphere;      // 纬度半球
}
