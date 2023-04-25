package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 更新/新增家人上传参数
 */
@ApiModel("更新/新增家人上传参数")
@Data
public class NewFamilyParams {
    @ApiModelProperty("用户id(新增家人不需要传递此参数)")
    private Long uid;
    @ApiModelProperty("用户名(更新家人不需要传此参数)")
    private String username;
    @ApiModelProperty("密码(更新家人不需要传此参数)")
    private String password;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("身份")
    private String identity;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("是否锁定")
    private boolean locked;
    @ApiModelProperty("是否控制后台")
    private boolean controlBackstage;

}
