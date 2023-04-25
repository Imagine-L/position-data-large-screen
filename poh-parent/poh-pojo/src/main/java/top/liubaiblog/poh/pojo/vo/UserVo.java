package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 留白
 * @description 用户视图层对象
 */
@Data
public class UserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;
    private String username;
    private String nickname;
    private String identity;
}
