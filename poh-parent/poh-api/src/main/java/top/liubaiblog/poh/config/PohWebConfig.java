package top.liubaiblog.poh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.liubaiblog.baselutils.spring.interceptor.HttpInterceptor;
import top.liubaiblog.poh.interceptor.UserInterceptor;
import top.liubaiblog.poh.pojo.properties.SecurityProperties;

/**
 * @author 留白
 * @description web配置类
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ComponentScan("top.liubaiblog.baselutils.spring")
public class PohWebConfig implements WebMvcConfigurer {

    @Autowired
    private HttpInterceptor httpInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置http全局拦截器
        registry.addInterceptor(httpInterceptor).addPathPatterns("/**");
        // 配置登录用户信息的拦截器，但是对于忽略的地址不拦截
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(securityProperties.getIgnoreAddress())
                .excludePathPatterns(securityProperties.getPermitAddress())
                .excludePathPatterns("/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**");
    }

    /**
     * 静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        // 配置swagger-ui显示文档
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
