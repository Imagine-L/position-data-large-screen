package top.liubaiblog.poh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author 留白
 * @deprecated 关于Swagger的配置类, ui界面可以见: http://host:port/swagger-ui.html
 */
@Configuration(proxyBeanMethods = false)
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {
        // 动态配置是否要显示Swagger的环境
        // 设置要显示swagger的环境
        Profiles profiles = Profiles.of("dev", "test");
        // 判断当前是否属于该环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("all")
                .enable(flag)       // 是否开启Swagger, 是个是false, 则浏览器无法访问
                .select()           // 配置扫描规则选择器
                .apis(RequestHandlerSelectors.basePackage("top.liubaiblog.poh.controller"))     // 指定要扫描的包
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("liubai", "http://www.liubaiblog.top/", "133*******@qq.com");      // 作者信息
        return new ApiInfo("识途系统接口文档"                      // 标题
                , "为客户端提供的接口API"                         // 描述
                , "v1.0"                                          // 版本
                , "http://www.liubaiblog.top/"                             // 组织连接
                , contact
                , null                                     // 许可
                , null,
                Collections.emptyList());                                         // 扩展
    }

}
