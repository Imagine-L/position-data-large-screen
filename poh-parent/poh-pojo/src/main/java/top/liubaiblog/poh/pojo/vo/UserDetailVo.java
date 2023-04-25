package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author 留白
 * @description 用户详情的识图层对象
 */
@Data
public class UserDetailVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;                   // 编号
    private String username;            // 用户名
    private String nickname;            // 昵称
    private String identity;            // 身份
    private String gender;              // 性别
    private boolean locked;             // 是否锁定
    private boolean controlBackstage;   // 是否允许控制后台
    private Date createTime;            // 创建时间
}
