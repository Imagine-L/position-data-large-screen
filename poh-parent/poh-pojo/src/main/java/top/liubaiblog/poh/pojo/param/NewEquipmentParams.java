package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 更新设备上传参数
 */
@Data
@ApiModel(description = "更新/添加设备请求参数")
public class NewEquipmentParams {
    @ApiModelProperty("设备码(SN码)")
    private String equipCode;
    @ApiModelProperty("设备名")
    private String equipName;
    @ApiModelProperty("分组ID")
    private Long groupId;
    @ApiModelProperty("是否为主设备")
    private boolean main;
    @ApiModelProperty("是否主页展示")
    private boolean showed;
    @ApiModelProperty("是否禁用设备")
    private boolean locked;
}
