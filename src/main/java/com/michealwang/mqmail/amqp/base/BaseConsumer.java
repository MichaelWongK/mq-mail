package com.michealwang.mqmail.amqp.base;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.io.IOException;


/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 17:13
 * @Description
 */
public interface BaseConsumer {

    void consume(Message message, Channel channel) throws IOException;
}
