package com.michealwang.mqmail.amqp.listener;

import com.michealwang.mqmail.amqp.proxy.BaseConsumerProxy;
import com.michealwang.mqmail.amqp.base.BaseConsumer;
import com.michealwang.mqmail.amqp.consumer.LoginLogConsumer;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.michealwang.mqmail.platform.service.MsgLogService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 20:07
 * @Description 登录日志消费者监听
 */
@Component
public class LoginLogListener {

    @Autowired
    private LoginLogConsumer loginLogConsumer;
    @Autowired
    private MsgLogService msgLogService;

    @RabbitListener(queues = RabbitConfig.LOGIN_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        System.out.println(">>> loginLog consume");
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(loginLogConsumer, msgLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }
}
