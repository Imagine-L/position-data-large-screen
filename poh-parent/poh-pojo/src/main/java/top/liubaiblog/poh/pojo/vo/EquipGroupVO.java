package top.liubaiblog.poh.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author 留白
 * @description
 */
@Data
public class EquipGroupVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long gid;               // 分组ID
    private String groupName;       // 分组名
    private String description;     // 分组描述
    private Integer count;          // 该分组的设备数量
}
