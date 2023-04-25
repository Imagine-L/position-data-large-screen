package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.baselutils.util.http.HttpResponseUtils;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.pojo.param.EquipmentParams;
import top.liubaiblog.poh.pojo.param.NewEquipmentParams;
import top.liubaiblog.poh.pojo.vo.EquipmentDetailVO;
import top.liubaiblog.poh.service.IBoughtEquipmentService;
import top.liubaiblog.poh.service.IEquipmentViewService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 留白
 * @description 设备控制器
 */
@RestController
@Api(tags = "设备控制器")
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private IEquipmentViewService equipmentViewService;

    @Autowired
    private IBoughtEquipmentService boughtEquipmentService;

    @ApiOperation("查询设备列表")
    @PreAuthorize("hasAuthority('equipment:operate:select')")
    @PostMapping("/list")
    public ResponseData list(@RequestBody(required = false) EquipmentParams equipmentParams) {
        return ResponseData.success(equipmentViewService.listByParams(equipmentParams));
    }

    @ApiOperation("查询某个设备的详细信息")
    @PreAuthorize("hasAuthority('equipment:operate:select')")
    @GetMapping("/{eid}")
    public ResponseData getById(@PathVariable("eid") Long eid) {
        EquipmentDetailVO equipmentDetail = equipmentViewService.getDetailVOById(eid);
        if (equipmentDetail != null) {
            return ResponseData.success(equipmentDetail);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("修改设备")
    @PreAuthorize("hasAuthority('equipment:operate:update')")
    @PutMapping
    public ResponseData update(@RequestBody NewEquipmentParams equipmentParams) {
        if (boughtEquipmentService.updateByParams(equipmentParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, false);
    }

    @ApiOperation("删除设备")
    @PreAuthorize("hasAuthority('equipment:operate:delete')")
    @DeleteMapping("/{eid}")
    public ResponseData delete(@PathVariable("eid") Long eid) {
        if (boughtEquipmentService.removeById(eid)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, false);
    }

    @ApiOperation("添加新设备")
    @PreAuthorize("hasAuthority('equipment:operate:insert')")
    @PostMapping
    public ResponseData save(@RequestBody NewEquipmentParams equipmentParams) {
        if (boughtEquipmentService.saveByParams(equipmentParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION, false);
    }

    @ApiOperation("获取全部设备的设备名")
    @PreAuthorize("hasAuthority('equipment:operate:select')")
    @GetMapping("/names")
    public ResponseData getNames() {
        return ResponseData.success(equipmentViewService.getNamesByUid());
    }

    @ApiOperation("查询前台中需要显示的设备")
    @GetMapping("/showed/list/{size}")
    public ResponseData getShowedList(@PathVariable("size") Integer size) {
        return ResponseData.success(equipmentViewService.listByShowed(size));
    }

}
