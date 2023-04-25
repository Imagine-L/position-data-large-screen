package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.pojo.param.FamilyParams;
import top.liubaiblog.poh.pojo.param.NewFamilyParams;
import top.liubaiblog.poh.pojo.param.UpdatePwdParams;
import top.liubaiblog.poh.pojo.param.UserParams;
import top.liubaiblog.poh.service.IUserService;

/**
 * @author 留白
 * @description
 */
@RestController
@Api(tags = "用户控制器")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("更新当前登录的用户信息(包括token)")
    @GetMapping("/update/info")
    public ResponseData updateLoginUser() {
        return ResponseData.success(userService.updateLoginUser());
    }

    @ApiOperation("更新用户信息(对用户表执行操作)")
    @PutMapping
    public ResponseData update(@RequestBody UserParams userParams) {
        if (userService.updateByParams(userParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("更新用户密码")
    @PutMapping("/pwd")
    public ResponseData updatePassword(@RequestBody UpdatePwdParams updatePwdParams) {
        if (userService.updatePassword(updatePwdParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("获取家人列表")
    @PreAuthorize("hasAuthority('user:family:operate:select')")
    @PostMapping("/family/list")
    public ResponseData getFamilyList(@RequestBody(required = false) FamilyParams familyParams) {
        return ResponseData.success(userService.listFamilyByParams(familyParams));
    }

    @ApiOperation("获取家人用户详细信息")
    @PreAuthorize("hasAuthority('user:family:operate:select')")
    @GetMapping("/family/{uid}")
    public ResponseData getFamilyDetail(@PathVariable("uid") Long uid) {
        return ResponseData.success(userService.getFamilyDetailById(uid));
    }

    @ApiOperation("更新家人用户信息")
    @PreAuthorize("hasAuthority('user:family:operate:update')")
    @PutMapping("/family")
    public ResponseData updateFamily(@RequestBody NewFamilyParams familyParams) {
        if (userService.updateFamilyByParams(familyParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("删除家人用户")
    @PreAuthorize("hasAuthority('user:family:operate:delete')")
    @DeleteMapping("/family/{uid}")
    public ResponseData deleteFamily(@PathVariable("uid") Long uid) {
        if (userService.removeFamilyById(uid)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

    @ApiOperation("添加一个家人")
    @PreAuthorize("hasAuthority('user:family:operate:insert')")
    @PostMapping("/family")
    public ResponseData saveFamily(@RequestBody NewFamilyParams familyParams) {
        if (userService.saveFamilyByParams(familyParams)) {
            return ResponseData.success(true);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_PARAM_EXCEPTION);
    }

}
