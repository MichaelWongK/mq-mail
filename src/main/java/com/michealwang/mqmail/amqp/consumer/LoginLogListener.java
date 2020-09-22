package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 20:07
 * @Description
 */
@Component
public class LoginLogListener {

    @Autowired
    private LoginLogConsumer loginLogConsumer;
    @Autowired
    private StringRedisUtils stringRedisUtils;

    @RabbitListener(queues = RabbitConfig.LOGIN_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        System.out.println(">>>consume");
        ConsumerProxy consumerProxy = new ConsumerProxy(loginLogConsumer, stringRedisUtils);
        BaseConsumer proxy = (BaseConsumer) consumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }
}
