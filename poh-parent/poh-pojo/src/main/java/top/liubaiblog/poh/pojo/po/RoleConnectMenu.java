package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_role_connect_menu")
@ApiModel(value = "RoleConnectMenu对象", description = "角色菜单关联表")
public class RoleConnectMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色菜单关联id")
    @TableId
    private Long rmid;

    @ApiModelProperty("角色id")
    private Long rid;

    @ApiModelProperty("菜单id")
    private Long mid;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastUpdateTime;

    public Long getRmid() {
        return rmid;
    }

    public void setRmid(Long rmid) {
        this.rmid = rmid;
    }
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
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
        return "RoleConnectMenu{" +
            "rmid=" + rmid +
            ", rid=" + rid +
            ", mid=" + mid +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", lastUpdateBy=" + lastUpdateBy +
            ", lastUpdateTime=" + lastUpdateTime +
        "}";
    }
}
