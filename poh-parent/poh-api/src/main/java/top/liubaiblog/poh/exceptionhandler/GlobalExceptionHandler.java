package top.liubaiblog.poh.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import top.liubaiblog.baselutils.util.http.HttpRequestUtils;
import top.liubaiblog.baselutils.util.http.HttpResponseUtils;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;
import top.liubaiblog.poh.common.exception.RegisterMessageException;
import top.liubaiblog.poh.common.exception.RemoveOwnException;
import top.liubaiblog.poh.pojo.dto.LoginUserDTO;
import top.liubaiblog.poh.common.threadlocal.UserThreadLocal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 留白
 * @description 全局异常处理器
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 捕获全部异常，并返回一个服务器异常信息
     */
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e) {
        e.printStackTrace();
        log.error("执行全局异常处理 -> {}", e.getMessage());
        HttpResponseUtils.write(ResponseData.build(ResponseDataEnum.SERVER_EXECUTE_EXCEPTION, e.getMessage()));
    }

    /**
     * 捕获客户端鉴权异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public void authenticationExceptionHandler(AuthenticationException e) {
        HttpServletRequest request = HttpRequestUtils.getLocalRequest();
        log.error("客户端鉴权异常 -> {}", e.getMessage());
        log.error("异常接口 -> {} | {}", request.getRequestURI(), request.getMethod());
        HttpResponseUtils.write(ResponseData.build(ResponseDataEnum.CLIENT_PERMISSION_EXCEPTION, e.getMessage()));
    }

    /**
     * 用户注册信息出错
     */
    @ExceptionHandler(RegisterMessageException.class)
    public void registerMessageException(RegisterMessageException e) {
        HttpServletRequest request = HttpRequestUtils.getLocalRequest();
        log.error("客户端注册失败 -> {}", e.getMessage());
        log.error("异常接口 -> {} | {}", request.getRequestURI(), request.getMethod());
        HttpResponseUtils.write(ResponseData.build(ResponseDataEnum.CLIENT_REGISTER_EXCEPTION, e.getMessage()));
    }

    /**
     * 用户访问被拒绝
     */
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(AccessDeniedException e) {
        LoginUserDTO loginUserDTO = UserThreadLocal.get();
        String username = loginUserDTO.getUsername();
        log.error("{} 访问被拒绝，原因可能是 -> {}", username, e.getMessage());
        HttpResponseUtils.write(ResponseData.build(ResponseDataEnum.CLIENT_PERMISSION_EXCEPTION, e.getMessage()));
    }

    /**
     * 用户自己删除自己
     */
    @ExceptionHandler(RemoveOwnException.class)
    public void removeOwnExceptionHandler(RemoveOwnException e) {
        LoginUserDTO loginUserDTO = UserThreadLocal.get();
        log.error("用户试图删除自己，用户id -> {}，错误信息 -> {}", loginUserDTO.getUid(), e.getMessage());
        HttpResponseUtils.write(ResponseData.fail("A0440", "用户不能删除自己"));
    }

}
