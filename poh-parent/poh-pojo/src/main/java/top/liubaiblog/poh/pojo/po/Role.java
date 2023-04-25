package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色id")
    @TableId
    private Long rid;

    @ApiModelProperty("角色标记")
    private String roleMark;

    @ApiModelProperty("角色名")
    private String roleName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("是否被锁定(1是,0否)")
    private Integer locked;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private LocalDateTime lastUpdateTime;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
    public String getRoleMark() {
        return roleMark;
    }

    public void setRoleMark(String roleMark) {
        this.roleMark = roleMark;
    }
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
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
        return "Role{" +
            "rid=" + rid +
            ", roleMark=" + roleMark +
            ", roleName=" + roleName +
            ", description=" + description +
            ", locked=" + locked +
            ", createBy=" + createBy +
            ", createTime=" + createTime +
            ", lastUpdateBy=" + lastUpdateBy +
            ", lastUpdateTime=" + lastUpdateTime +
        "}";
    }
}
