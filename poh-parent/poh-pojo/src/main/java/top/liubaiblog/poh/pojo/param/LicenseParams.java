package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 登录和注册参数
 */
@Data
@ApiModel(description = "认证许可请求参数")
public class LicenseParams {
    @ApiModelProperty("用户名(如果是注册场景，需要确保用户名不重复)")
    private String username;
    @ApiModelProperty("密码(已经通过md5加密后的密码)")
    private String password;
    @ApiModelProperty("设备码(登录场景不允许传此参数)")
    private String equipCode;
}
