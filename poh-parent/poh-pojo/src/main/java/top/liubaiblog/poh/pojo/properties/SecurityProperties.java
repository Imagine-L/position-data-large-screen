package top.liubaiblog.poh.pojo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author 留白
 * @description 安全相关配置
 */
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    String[] ignoreAddress;     // 忽略的地址
    String[] permitAddress;     // 允许访问的地址
}
