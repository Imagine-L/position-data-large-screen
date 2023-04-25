package top.liubaiblog.iotclientconnection.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.liubaiblog.iotclientconnection.properties.IotClientProperties;

/**
 * @author 留白
 * @description 连接iot平台相关工具
 */
@Component
public class IotClientUtils {

    @Autowired
    private IotClientProperties iotClientProperties;

    /**
     * 拼接用户名
     */
    public String joinUsername(int connectionCount) {
        long timeStamp = System.currentTimeMillis();
        String userName = iotClientProperties.getClientId() + "-" + connectionCount + "|authMode=aksign"
                + ",signMethod=" + iotClientProperties.getSignMethod()
                + ",timestamp=" + timeStamp
                + ",authId=" + iotClientProperties.getAccessKey()
                + ",iotInstanceId=" + iotClientProperties.getIotInstanceId()
                + ",consumerGroupId=" + iotClientProperties.getConsumerGroupId()
                + "|";
        return userName;
    }

}
