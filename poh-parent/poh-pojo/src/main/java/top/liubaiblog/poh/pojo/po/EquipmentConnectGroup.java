package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 设备及分组关联表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_equipment_connect_group")
@ApiModel(value = "EquipmentConnectGroup对象", description = "设备及分组关联表")
public class EquipmentConnectGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备及分组关联id")
    @TableId
    private Long egid;

    @ApiModelProperty("设备id")
    private Long eid;

    @ApiModelProperty("分组id")
    private Long gid;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastUpdateTime;

    public Long getEgid() {
        return egid;
    }

    public void setEgid(Long egid) {
        this.egid = egid;
    }
    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }
    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
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
        return "EquipmentConnectGroup{" +
            "egid=" + egid +
            ", eid=" + eid +
            ", gid=" + gid +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", lastUpdateBy=" + lastUpdateBy +
            ", lastUpdateTime=" + lastUpdateTime +
        "}";
    }
}
