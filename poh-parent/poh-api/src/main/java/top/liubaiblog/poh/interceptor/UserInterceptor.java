package top.liubaiblog.poh.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.liubaiblog.baselutils.spring.util.redis.RedisUtils;
import top.liubaiblog.baselutils.util.http.HttpRequestUtils;
import top.liubaiblog.poh.common.constant.SecureConstants;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 留白
 * @description 对登录用户进行拦截，获取当前
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从redis中获取登录的用户
        String token = request.getHeader(HttpRequestUtils.AUTHORIZATION_HEADER);
        LoginUserDTO loginUser = redisUtils.valGetObject(SecureConstants.REDIS_LOGIN_USER_TOKEN_PREFIX + token, LoginUserDTO.class);
        log.info("检测到登录用户 -> {}", loginUser.getUsername());
        // 添加到本地线程中
        UserThreadLocal.put(loginUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
