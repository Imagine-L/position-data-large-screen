package top.liubaiblog.poh.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_bought_equipment")
@ApiModel(value = "BoughtEquipment对象", description = "设备表")
@Data
public class BoughtEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("设备id")
    @TableId
    private Long beid;

    @ApiModelProperty("设备名")
    private String equipName;

    @ApiModelProperty("设备码(购买设备获取)")
    private String equipCode;

    @ApiModelProperty("设备归属者(若无则为空)")
    private Long equipOwner;

    @ApiModelProperty("是否设备处于危险状态(0否，1是)")
    private Boolean danger;

    @ApiModelProperty("是否主页展示(0否，1是)")
    private Boolean showed;

    @ApiModelProperty("是否为主设备(0否，1是)")
    private Boolean main;

    @ApiModelProperty("是否锁定设备(0否，1是)")
    private Boolean locked;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateTime;

    @ApiModelProperty("设备分组id")
    private Long groupId;
}
