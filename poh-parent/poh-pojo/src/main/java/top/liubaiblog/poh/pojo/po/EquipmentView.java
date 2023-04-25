package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName poh_equipment_view
 */
@TableName(value = "poh_equipment_view")
@Data
public class EquipmentView implements Serializable {
    /**
     * 设备id
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long eid;

    /**
     * 设备名
     */
    private String equipName;

    /**
     * 设备码(购买设备获取)
     */
    private String equipCode;

    /**
     * 设备归属者(若无则为空)
     */
    private Long equipOwner;

    /**
     * 是否设备处于危险状态(0否，1是)
     */
    private Boolean danger;

    /**
     * 分组ID
     */
    private Long groupId;

    /**
     * 是否主页展示(0否，1是)
     */
    private Boolean showed;

    /**
     * 是否为主设备(0否，1是)
     */
    private Boolean main;

    /**
     * 是否锁定设备(0否，1是)
     */
    private Boolean locked;

    /**
     * 电池容量
     */
    private Integer batteryCapacity;

    /**
     * 设备版本
     */
    private String version;

    /**
     * 设备类型
     */
    private String type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}