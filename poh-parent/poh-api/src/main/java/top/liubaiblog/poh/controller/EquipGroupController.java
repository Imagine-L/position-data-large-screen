package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.pojo.param.EquipGroupParams;
import top.liubaiblog.poh.pojo.param.NewEquipGroupParams;
import top.liubaiblog.poh.service.IEquipGroupService;
import top.liubaiblog.poh.service.IEquipmentViewService;

/**
 * @author 留白
 * @description 设备分组控制器
 */
@Api(tags = "设备分组控制器")
@RestController
@RequestMapping("/equipGroup")
public class EquipGroupController {

    @Autowired
    private IEquipGroupService groupService;

    @Autowired
    private IEquipmentViewService equipmentViewService;

    @ApiOperation("获取该用户所有设备分组的名字列表")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:select')")
    @GetMapping("/names")
    public ResponseData getNames() {
        return ResponseData.success(groupService.getNamesByUid());
    }

    @ApiOperation("/获取分组列表")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:select')")
    @PostMapping("/list")
    public ResponseData list(@RequestBody(required = false) EquipGroupParams groupParams) {
        return ResponseData.success(groupService.listByParams(groupParams));
    }

    @ApiOperation("查看某个组的所有设备")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:select')")
    @GetMapping("/equipments/{gid}")
    public ResponseData listEquipmentsByGid(@PathVariable("gid") Long gid) {
        return ResponseData.success(equipmentViewService.listByGroupId(gid));
    }

    @ApiOperation("查看某个分组的详细信息")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:select')")
    @GetMapping("/{gid}")
    public ResponseData getById(@PathVariable("gid") Long gid) {
        return ResponseData.success(groupService.getDetailById(gid));
    }

    @ApiOperation("更新分组信息")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:update')")
    @PutMapping
    public ResponseData update(@RequestBody NewEquipGroupParams groupParams) {
        if (groupService.updateByParams(groupParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("删除分组(需要确保分组中已无设备，否则删除失败)")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:delete')")
    @DeleteMapping("/{gid}")
    public ResponseData delete(@PathVariable("gid") Long gid) {
        if (equipmentViewService.isGroupOwner(gid)) {
            return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, "请移除该分组内的设备后重试");
        }
        if (groupService.removeById(gid)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, "该分组不存在");
    }

    @ApiOperation("添加分组")
    @PreAuthorize("hasAuthority('equipmentGroup:operate:insert')")
    @PostMapping
    public ResponseData save(@RequestBody NewEquipGroupParams groupParams) {
        if (groupService.saveByParams(groupParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

}
