package top.liubaiblog.poh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 留白
 * @description
 */
@RestController
@Profile({"dev", "test"})
@Api(tags = "测试接口控制器")
public class TestController {

    @GetMapping("/test")
    @ApiOperation("如果你可以访问此接口，说明可以认证成功")
    public String test() {
        return "<h1>HelloWorld</h1>";
    }

}
