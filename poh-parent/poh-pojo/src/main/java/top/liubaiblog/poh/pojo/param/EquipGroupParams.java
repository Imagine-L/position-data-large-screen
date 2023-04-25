package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description
 */
@Data
@ApiModel(description = "设备分组请求参数")
public class EquipGroupParams {
    @ApiModelProperty("当前用户的id(不需要客户端传递)")
    private Long uid;
    @ApiModelProperty("分组名")
    private String groupName;
    @ApiModelProperty("分组描述")
    private String description;
    @ApiModelProperty("该分组的设备数量")
    private Integer count;
}
