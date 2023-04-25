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
 * 菜单表
 * </p>
 *
 * @author liubai
 * @since 2022-06-15
 */
@TableName("poh_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
@Data
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单id")
    @TableId
    private Long mid;

    @ApiModelProperty("父菜单id")
    private Long parentMid;

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("权限")
    private String permission;

    @ApiModelProperty("是否被锁定(1是,0否)")
    private Integer locked;

    @ApiModelProperty("创建者")
    private Long createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改者")
    private Long lastUpdateBy;

    @ApiModelProperty("最后修改时间")
    private Date lastUpdateTime;
}
