package top.liubaiblog.poh.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.liubaiblog.poh.pojo.vo.EquipmentVO;

import java.util.List;

/**
 * @author 留白
 * @description 更新/新增分组请求参数
 */
@ApiModel(description = "更新/新增分组请求参数")
@Data
public class NewEquipGroupParams {
    @ApiModelProperty("分组id(若为新增操作此项可为空)")
    private Long gid;
    @ApiModelProperty("分组名")
    private String groupName;
    @ApiModelProperty("分组描述")
    private String description;
    @ApiModelProperty("该分组的所有设备")
    private List<Long> equipmentIds;
    @ApiModelProperty("该分组的设备是否展示")
    private boolean equipmentsShowed;
    @ApiModelProperty("该分组的所有设备是否锁定")
    private boolean equipmentsLocked;

}
