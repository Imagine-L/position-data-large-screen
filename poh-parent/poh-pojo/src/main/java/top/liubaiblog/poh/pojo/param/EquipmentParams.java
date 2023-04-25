package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "查询设备请求参数")
public class EquipmentParams {
    @ApiModelProperty("当前用户的id(不需要客户端传递)")
    private Long uid;
    @ApiModelProperty("设备名")
    private String equipName;
    @ApiModelProperty("设备所在组名")
    private String groupName;
    @ApiModelProperty("设备码")
    private String equipCode;
}
