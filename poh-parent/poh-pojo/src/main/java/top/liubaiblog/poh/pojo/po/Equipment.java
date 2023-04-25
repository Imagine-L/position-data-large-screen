package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_equipment")
@ApiModel(value = "Equipment对象", description = "设备表")
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备id")
    @TableId
    private Long eid;

    @ApiModelProperty("设备码(购买设备获取)")
    private String equipCode;

    @ApiModelProperty("电池容量")
    private Integer batteryCapacity;

    @ApiModelProperty("设备版本")
    private String version;

    @ApiModelProperty("设备类型")
    private String type;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastUpdateTime;

    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }
    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }
    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "Equipment{" +
            "eid=" + eid +
            ", equipCode=" + equipCode +
            ", batteryCapacity=" + batteryCapacity +
            ", version=" + version +
            ", type=" + type +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", lastUpdateBy=" + lastUpdateBy +
            ", lastUpdateTime=" + lastUpdateTime +
        "}";
    }
}
