package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 用户参数
 */
@ApiModel(description = "用户参数")
@Data
public class UserParams {
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("性别")
    private Integer gender;
}
