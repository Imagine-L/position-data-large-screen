package top.liubaiblog.poh.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.poh.pojo.properties.SecurityProperties;
import top.liubaiblog.poh.security.entrypoint.UnAuthEntryPoint;
import top.liubaiblog.poh.security.filter.TokenAuthenticationFilter;
import top.liubaiblog.poh.security.pass.Md5PasswordEncoder;

/**
 * @author 留白
 * @description
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Md5PasswordEncoder md5PasswordEncoder;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(md5PasswordEncoder);
    }

    /**
     * 配置认证规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()                    // 自定义登录页面
                .loginPage("/login.html")        // 登录页面跳转的登录路径
                .permitAll();  // 登录成功后默认跳转的路径

        // 设置拦截的路径
        http.authorizeRequests()
                .antMatchers(securityProperties.getPermitAddress()).permitAll()     // 不拦截的路径
                .antMatchers("/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**").permitAll()  // 不拦截swagger的路径
                .anyRequest()
                .authenticated();

        // 不通过session获取认证信息
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 添加认证出现异常的处理器
        http.exceptionHandling().authenticationEntryPoint(new UnAuthEntryPoint());

        // 添加自定义过滤器，进行授权
        AuthenticationManager authenticationManager = authenticationManager();
        http.addFilter(new TokenAuthenticationFilter(authenticationManager, redisUtils));

        // 关闭csrf防护
        http.csrf().disable();
    }

    /**
     * 忽略静态资源目录
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(securityProperties.getIgnoreAddress());
    }

    /**
     * 将authenticationManager注入到IOC容器
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
