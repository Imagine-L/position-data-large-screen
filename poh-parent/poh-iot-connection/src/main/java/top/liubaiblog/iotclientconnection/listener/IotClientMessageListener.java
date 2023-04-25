package top.liubaiblog.iotclientconnection.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import top.liubaiblog.poh.common.constant.IotClientConstants;
import top.liubaiblog.poh.common.message.EquipLocationMessage;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author 留白
 * @description 监听iot平台的消息监听器
 */
@Slf4j
@Component
public class IotClientMessageListener implements MessageListener {

    @Autowired
    @SuppressWarnings({"all"})
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TaskExecutor taskExecutor;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 接收到消息
     */
    @Override
    public void onMessage(final Message message) {
        try {
            //1.收到消息之后一定要ACK。
            // 推荐做法：创建Session选择Session.AUTO_ACKNOWLEDGE，这里会自动ACK。
            // 其他做法：创建Session选择Session.CLIENT_ACKNOWLEDGE，这里一定要调message.acknowledge()来ACK。
            // message.acknowledge();
            //2.建议异步处理收到的消息，确保onMessage函数里没有耗时逻辑。
            // 如果业务处理耗时过程过长阻塞住线程，可能会影响SDK收到消息后的正常回调。
            taskExecutor.execute(() -> processMessage(message));
        } catch (Exception e) {
            log.error("Submit task occurs exception -> {}\n{} ", e.getMessage(), e.getStackTrace());
        }
    }

    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     * 我们需要把iot平台的消息转发到rabbitmq中
     */
    protected void processMessage(Message message) {
        byte[] body = null;
        String content = null;
        String messageId = null;
        try {
            // 获取消息相关参数
            body = message.getBody(byte[].class);
            content = new String(body);
            /// 这两个字段暂时无用，但是怕以后有用时忘记怎么获取，留着记录
            // topic = message.getStringProperty("topic");
            messageId = message.getStringProperty("messageId");
        } catch (JMSException e) {
            log.error("ProcessMessage occurs error -> {}\n{}", e.getMessage(), e.getStackTrace());
        }
        if (content == null) return;
        EquipLocationMessage location = null;
        try {
            // 对定位消息进行验证，验证不通过则直接返回
            location = objectMapper.readValue(content, EquipLocationMessage.class);
            if (location == null) {
                return;
            }
        } catch (JsonProcessingException e) {
            log.error("Location json convert fail, message -> {}", content);
            return;
        }
        // 封装消息
//        org.springframework.amqp.core.Message rabbitMsg = MessageBuilder
//                .withBody(body)
//                .setMessageId(messageId)
//                .setExpiration(IotClientConstants.MESSAGE_EXPIRATION_MILLIS + "")
//                .build();
//        // 将消息发送到定位交换机(由于是扇出交换机，采用广播消息，不需要路由key)
//        rabbitTemplate.send(IotClientConstants.LOCATION_EXCHANGE,
//                "",
//                rabbitMsg);
        rabbitTemplate.convertAndSend(IotClientConstants.LOCATION_EXCHANGE,
                "",
                location,
                locationMsg -> {
            locationMsg.getMessageProperties().setExpiration(IotClientConstants.MESSAGE_EXPIRATION_MILLIS + "");
            return locationMsg;
        });
        log.info("send message info: \n" +
                        "exchange -> {}\n" +
                        "binding -> {}\n" +
                        "message -> {}", IotClientConstants.LOCATION_EXCHANGE,
                IotClientConstants.REAL_TIME_QUEUE_BINDING_EXCHANGE,
                content);
    }

}
