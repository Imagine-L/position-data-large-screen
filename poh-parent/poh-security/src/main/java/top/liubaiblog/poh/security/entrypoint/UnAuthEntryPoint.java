package top.liubaiblog.poh.security.entrypoint;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import top.liubaiblog.baselutils.util.http.HttpResponseUtils;
import top.liubaiblog.baselutils.vo.ResponseData;
import top.liubaiblog.baselutils.vo.enums.ResponseDataEnum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 留白
 * @description 出现认证异常后的处理方式
 */
public class UnAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证异常处理方式
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        HttpResponseUtils.write(response, ResponseData.build(ResponseDataEnum.CLIENT_PERMISSION_EXCEPTION));
    }

}
