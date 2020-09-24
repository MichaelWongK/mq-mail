package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.amqp.MessageHelper;
import com.michealwang.mqmail.amqp.base.BaseConsumer;
import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.util.mail.MailUtil;
import com.michealwang.mqmail.config.exception.ServiceException;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
import com.michealwang.mqmail.platform.pojo.Mail;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 22:03
 * @Description
 */
@Slf4j
@Component
public class MailConsumer implements BaseConsumer {

    @Autowired
    private MailUtil mailUtil;

    @Override
    public void consume(Message message, Channel channel) {

        log.info("mail consumer 收到消息: {}", message);
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        boolean flag = mailUtil.send(mail);
        if (!flag) {
            throw new ServiceException("send mail error");
        }
    }
}
