package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.pojo.param.LicenseParams;
import top.liubaiblog.poh.service.IUserService;
import top.liubaiblog.poh.service.LicenseService;

/**
 * @author 留白
 * @description 登录控制器
 */
@RestController
@Api(tags = "访问许可控制器")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @Autowired
    private IUserService userService;

    @ApiOperation("判断用户名是否存在")
    @GetMapping("/checkUser/{username}")
    public ResponseData checkUser(@PathVariable("username") String username) {
        return ResponseData.success(userService.isUsernameExist(username));
    }

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public ResponseData login(@RequestBody LicenseParams licenseParams) {
        LoginUserDTO loginUser = licenseService.loginGetLoginUser(licenseParams);
        if (loginUser != null) {
            return ResponseData.success(loginUser);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_LOGIN_EXCEPTION);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/register")
    public ResponseData register(@RequestBody LicenseParams licenseParams) {
        LoginUserDTO loginUser = licenseService.registerGetLoginUser(licenseParams);
        if (loginUser != null) {
            return ResponseData.success(loginUser);
        }
        return ResponseData.build(ResponseDataEnum.CLIENT_LOGIN_EXCEPTION);
    }

    @ApiOperation("用户登出接口")
    @GetMapping("/logout")
    public ResponseData logout() {
        licenseService.logout();
        return ResponseData.success(null);
    }

}
