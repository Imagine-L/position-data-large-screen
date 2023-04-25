package top.liubaiblog.poh.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import top.liubaiblog.baselutils.spring.util.jwt.JwtUtils;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.baselutils.util.http.HttpRequestUtils;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 留白
 * @description 授权过滤器
 */
@Slf4j
public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    private RedisUtils redisUtils;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, RedisUtils redisUtils) {
        super(authenticationManager);
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获取当前认证成功的用户权限信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        // 如果权限信息不为空，则放入上下文
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 放行
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        try {
            // 获取token并进行非空校验
            String token = request.getHeader(HttpRequestUtils.AUTHORIZATION_HEADER);
            if (!StringUtils.hasLength(token)) {
                log.warn("客户端token为空");
                return null;
            }
            // 解析token
            Map<String, Object> parse = JwtUtils.parse(token);
            if (parse == null || parse.isEmpty()) {
                log.warn("客户端token解析异常");
                return null;
            }
            // 从redis中获取登录的用户及其权限
            LoginUserDTO loginUser = redisUtils.valGetObject(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token, LoginUserDTO.class);
            List<String> menus = redisUtils.valGetObject(SecureConstants.REDIS_PERMISSION_PREFIX + token, List.class);
            if (loginUser == null || menus == null) {
                log.warn("客户端token已过期或者为伪造token");
                return null;
            }
            List<GrantedAuthority> permissions = menus.stream()
                    .map(permission -> (GrantedAuthority) () -> permission)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getToken(), permissions);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
