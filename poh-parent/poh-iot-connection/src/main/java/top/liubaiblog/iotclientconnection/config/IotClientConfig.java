package top.liubaiblog.iotclientconnection.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnection;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.liubaiblog.iotclientconnection.listener.IotClientJmsConnectionListener;
import top.liubaiblog.iotclientconnection.listener.IotClientMessageListener;
import top.liubaiblog.iotclientconnection.properties.IotClientProperties;
import top.liubaiblog.iotclientconnection.util.IotClientUtils;
import top.liubaiblog.poh.common.config.RabbitMqConfig;
import top.liubaiblog.poh.common.constant.IotClientConstants;

import javax.annotation.PostConstruct;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author 留白
 * @description 连接iot平台的配置类
 */
@Slf4j
@Configuration
@Import(RabbitMqConfig.class)
@EnableConfigurationProperties(IotClientProperties.class)
public class IotClientConfig {

    @Autowired
    private IotClientProperties iotClientProperties;

    @Autowired
    private IotClientUtils iotClientUtils;

    @Autowired
    private IotClientMessageListener messageListener;

    /**
     * 执行初始化操作，声明交换机队列等
     */
    @PostConstruct
    public void init() {
        log.info("iot client successfully connected to rabbit");
    }

    /**
     * 创建一个连接集合，连接iot平台
     */
    @Bean
    public List<Connection> listConnections() throws Exception {
        List<Connection> connections = new ArrayList<>();
        Hashtable<String, String> hashtable = new Hashtable<>(5);
        hashtable.put("connectionfactory.SBCF", iotClientProperties.getConnectionUrl());
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");

        //参数说明，请参见AMQP客户端接入说明文档。
        for (int i = 0; i < iotClientProperties.getConnectionCount(); i++) {
            Context context = new InitialContext(hashtable);
            ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
            Destination queue = (Destination) context.lookup("QUEUE");
            // 创建连接。
            Connection connection = cf.createConnection(iotClientUtils.joinUsername(i), iotClientProperties.getPassword());
            connections.add(connection);

            ((JmsConnection) connection).addConnectionListener(new IotClientJmsConnectionListener());
            // 创建会话。
            // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()。
            // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）。
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            connection.start();
            // 创建Receiver连接。
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(messageListener);
        }
        log.info("iot client connection is started successfully");
        return connections;
    }
}