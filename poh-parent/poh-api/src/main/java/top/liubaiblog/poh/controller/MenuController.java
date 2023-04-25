package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.poh.service.IMenuService;

import java.util.Arrays;

/**
 * @author 留白
 * @description 菜单控制器
 */
@RestController
@Api(tags = "菜单控制器(限制用户能访问哪些菜单)")
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @ApiOperation("用户是否能进入后台")
    @GetMapping("/backstage")
    public ResponseData toBackstage() {
        // 如果有对设备的操作权限和对设备分组的操作权限，则视为有前往后台的权限
        boolean isPermit = menuService.hasAllPermissions(Arrays.asList("equipment:operate", "equipmentGroup:operate"));
        return ResponseData.success(isPermit);
    }

}
