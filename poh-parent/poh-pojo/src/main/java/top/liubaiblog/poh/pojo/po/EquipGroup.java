package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 设备分组表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_equip_group")
@ApiModel(value = "EquipGroup对象", description = "设备分组表")
@Data
public class EquipGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备组id")
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId
    private Long gid;

    @ApiModelProperty("分组名")
    private String groupName;

    @ApiModelProperty("分组描述")
    private String description;

    @ApiModelProperty("分组归属者")
    private Long groupOwner;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateTime;
}
