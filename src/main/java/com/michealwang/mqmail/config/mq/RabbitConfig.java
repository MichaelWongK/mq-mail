package com.michealwang.mqmail.config.mq;

import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
import com.michealwang.mqmail.platform.mapper.UserMapper;
import com.michealwang.mqmail.platform.pojo.MsgLog;
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
    private MsgLogMapper msgLogMapper;

    @Autowired
    private CachingConnectionFactory connectionFactory;


    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate =new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("消息发送ack: {}", ack);
            if (ack) {
                log.info("消息成功发送到Exchange");

                // 修改消息状态为成功
                String msgId = correlationData.getId();
                MsgLog msgLog = new MsgLog();
                msgLog.setMsgId(msgId);
                msgLog.setStatus(Constant.MsgLogStatus.SUCCESS);
                msgLogMapper.updateStatus(msgLog);
            } else {
                log.info("消息发送到Exchange失败: correlationData: {}, cause: {}", correlationData, cause);
            }
        });

        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}",
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
