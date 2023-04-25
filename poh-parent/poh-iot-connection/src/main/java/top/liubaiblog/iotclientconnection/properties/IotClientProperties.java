package top.liubaiblog.iotclientconnection.properties;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * @author 留白
 * @description 连接iot平台相关配置
 */
@ConfigurationProperties(prefix = "poh.iot.client")
@Data
public class IotClientProperties {

    private String accessKey = "LTAI5t9CZo9uFM8JvMHKYyo8";
    private String accessSecret = "PiF86JIx0hrowoyL4Vyqzl0xzaWRsT";
    private String consumerGroupId = "DEFAULT_GROUP";

    // iotInstanceId：实例ID。若是2021年07月30日之前（不含当日）开通的公共实例，请填空字符串。
    private String iotInstanceId = "iot-06z00cqg67j37zl";

    // 控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
    // 建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。
    private String clientId = "XB001";

    //${YourHost}为接入域名，请参见AMQP客户端接入说明文档。
    private String host = "1293335680260657.iot-amqp.cn-shanghai.aliyuncs.com";

    // 指定单个进程启动的连接数
    // 单个连接消费速率有限，请参考使用限制，最大64个连接
    // 连接数和消费速率及rebalance相关，建议每500QPS增加一个连接
    private int connectionCount = 4;

    // 签名方法：支持hmacmd5、hmacsha1和hmacsha256。
    String signMethod = "hmacsha1";

    /**
     * 计算签名，password组装方法，请参见AMQP客户端接入说明文档。
     */
    public String getSignContent() {
        if (!StringUtils.hasLength(accessKey)) return "";
        return "authId=" + accessKey + "&timestamp=" + System.currentTimeMillis();
    }

    /**
     * 获取密码
     */
    public String getPassword() {
        try {
            return doSign(getSignContent(), accessSecret, signMethod);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 拼接连接地址
     */
    public String getConnectionUrl() {
        return "failover:(amqps://" + host + ":5671?amqp.idleTimeout=80000)"
                + "?failover.reconnectDelay=30";
    }

    /**
     * 计算签名，password组装方法，请参见AMQP客户端接入说明文档。
     */
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }

}
