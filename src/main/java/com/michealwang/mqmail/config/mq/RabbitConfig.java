package com.michealwang.mqmail.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 22:48
 * @Description rabbitmq配置
 */
@Slf4j
@Configuration
public class RabbitConfig {

    // 直连交换机
    @Value("${log.login.directexchange}")
    private   String loginLogExchange;
    // 日志队列
    @Value("${log.login.queue}")
    private String loginLogQueueName;
    // 日志路由
    @Value("${log.login.routing}")
    private String loginLogRoutingKey;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirmType(rabbitProperties.getPublisherConfirmType());
        // PublisherReturns 保证消息对Broker端是可达的，如果出现路由键不可达的情况，则使用监听器对不可达的消息进行后续的处理，保证消息的路由成功：RabbitTemplate.ReturnCallback
        connectionFactory.setPublisherReturns(rabbitProperties.isPublisherReturns());

        RabbitTemplate rabbitTemplate =new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        // 使用return-callback时必须设置mandatory为true
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息发送成功: correlationData: {}", correlationData);
            } else {
                log.info("消息发送失败: correlationData: {}, cause: {}", correlationData, cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息丢失: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}",
                        exchange, routingKey, replyCode, replyText, message);
            }
        });

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue loginLogQueue() {
        return new Queue(loginLogQueueName, true);
    }

    /**
     * 登陆日志直连交换机注册
     * 入参：交换机名, 持久化, 自动删除
     * @return
     */
    @Bean
    public DirectExchange loginLogDirectExchange() {
        return new DirectExchange(loginLogExchange, true, false);
    }

    /**
     * 绑定日志队列、直连交换机、日志路由
     * @return
     */
    @Bean
    public Binding loginLogBinding() {
        return BindingBuilder.bind(loginLogQueue()).to(loginLogDirectExchange()).with(loginLogRoutingKey);
    }
}
