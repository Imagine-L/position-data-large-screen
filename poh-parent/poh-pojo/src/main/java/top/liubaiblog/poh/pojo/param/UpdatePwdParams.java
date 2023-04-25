package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 留白
 * @description 修改密码参数
 */
@Data
@ApiModel(description = "修改密码参数")
public class UpdatePwdParams {
    @ApiModelProperty("原密码(客户端需要对明文密码进行加密后传输)")
    private String oldPwd;
    @ApiModelProperty("新密码(已经加密的密文密码)")
    private String newPwd;
}
