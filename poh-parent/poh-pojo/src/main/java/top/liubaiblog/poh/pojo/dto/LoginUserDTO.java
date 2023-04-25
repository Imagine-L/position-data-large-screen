package top.liubaiblog.poh.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 留白
 * @description 已经登录的用户
 */
@Data
public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用于查询的用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long queryUid;

    @ApiModelProperty("用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("用户身份")
    private String identity;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("是否能够前往后台")
    private boolean toBackstage;

    @ApiModelProperty("ip地址")
    private String remoteIp;

    @ApiModelProperty("系统")
    private String system;

    @ApiModelProperty("浏览器")
    private String browser;

    @ApiModelProperty("登录用户令牌")
    private String token;
}
