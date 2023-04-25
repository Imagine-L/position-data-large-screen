package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 家人参数
 */
@ApiModel(description = "查询家人参数")
@Data
public class FamilyParams {
    @ApiModelProperty("家人用户名")
    private String username;
    @ApiModelProperty("家人名")
    private String nickname;
    @ApiModelProperty("家人身份")
    private String identity;
}
