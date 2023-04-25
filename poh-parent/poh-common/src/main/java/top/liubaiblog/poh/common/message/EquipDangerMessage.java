package top.liubaiblog.poh.common.message;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 留白
 * @description 设备是否危险的经纬度
 */
@Data
public class EquipDangerMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String equipCode;               // 设备码
    private Boolean danger;                 // 设备是否发起求救消息
}
