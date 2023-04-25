package top.liubaiblog.poh.common.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.liubaiblog.poh.common.constant.IotClientConstants;

/**
 * @author 留白
 * @description rabbitmq配置类
 */
@Configuration
@EnableRabbit
public class RabbitMqConfig {

    /**
     * 定位交换机
     */
    @Bean
    public FanoutExchange locationExchange() {
        return new FanoutExchange(IotClientConstants.LOCATION_EXCHANGE);
    }

    /**
     * 用户交换机，为每个用户发消息的交换机
     */
    @Bean("userExchange")
    public DirectExchange userExchange() {
        return new DirectExchange(IotClientConstants.USER_EXCHANGE);
    }

    /**
     * 实时定位的队列
     */
    @Bean
    public Queue realTimePositionQueue() {
        return QueueBuilder
                .durable(IotClientConstants.REAL_TIME_POSITION_QUEUE)
                .build();
    }

    /**
     * 间隔定位消息分发队列
     */
    @Bean
    public Queue intervalLocationReleaseQueue() {
        return QueueBuilder
                .durable(IotClientConstants.INTERVAL_LOCATION_RELEASE_QUEUE)
                .build();
    }

    /**
     * 绑定实时定位队列和定位交换机
     */
    @Bean
    public Binding realTimeQueueBindingExchange() {
        return BindingBuilder
                .bind(realTimePositionQueue())
                .to(locationExchange());
    }

    /**
     * 绑定间隔定位消息分发队列和定位交换机
     */
    @Bean
    public Binding intervalQueueBindingExchange() {
        return BindingBuilder
                .bind(intervalLocationReleaseQueue())
                .to(locationExchange());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
