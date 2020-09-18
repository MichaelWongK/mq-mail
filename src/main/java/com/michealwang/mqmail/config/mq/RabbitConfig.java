package com.michealwang.mqmail.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 22:48
 * @Description rabbitmq配置
 */
@Configuration
public class RabbitConfig {

    // 直连交换机
    public static final String DIRECT_EXCHANGE_NAME = "direct.exchange";
    // 主题订阅交换机
    public static final String TOPIC_EXCHANGE_NAME = "topic.exchange";
    // 广播模式
    public static final String FANOUT_EXCHANGE_NAME = "fanout.exchange";

    // 日志队列
    public static final String LOG_QUEUE_NAME = "log.queue.name";
    // 日志路由
    public static final String LOG_ROUTING_KEY_NAME = "log.routing.name";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue logQueue() {
        return new Queue(LOG_QUEUE_NAME, true);
    }

    /**
     * 绑定日志队列、直连交换机、日志路由
     * @return
     */
    @Bean
    public Binding logBinding() {
        return BindingBuilder.bind(logQueue()).to(directExchange()).with(LOG_ROUTING_KEY_NAME);
    }
}
