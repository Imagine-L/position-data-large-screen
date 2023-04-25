package top.liubaiblog.poh.security.pass;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author 留白
 * @description 密码处理
 */
@Component
public class Md5PasswordEncoder implements PasswordEncoder {

    /**
     * 密码加密
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(String.valueOf(rawPassword).getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将未加工的密码和已加工的密码进行比对
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.hasLength(rawPassword) && StringUtils.hasLength(encodedPassword)) {
            return encode(rawPassword).equals(encodedPassword);
        }
        return false;
    }
}
