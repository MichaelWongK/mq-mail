package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.common.util.RandomUtil;
import com.michealwang.mqmail.platform.pojo.LoginLog;
import com.michealwang.mqmail.platform.service.LoginLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 14:02
 * @Description 登录日志消费
 */
@Slf4j
@Component
public class LoginLogConsumer implements BaseConsumer {

    @Autowired
    private LoginLogService loginLogService;

    @Override
    public void consume(Message message, Channel channel) throws IOException {
        long tag =message.getMessageProperties().getDeliveryTag();
        try {
            log.info("loginLog1 消费消息：{}", message.toString());
            LoginLog loginLog = MessageHelper.msgToObj(message, LoginLog.class);
            loginLog.setId(RandomUtil.generateDigitalStr(5));
            LoginLog lLog = loginLogService.selectByMsgId(loginLog.getMsgId());
            if (null == lLog) {
                loginLogService.insert(loginLog);
            }
        } catch (Exception e) {
            log.error("logUserConsumer error:" + e);
            channel.basicNack(tag, false, true);
        } finally {
            channel.basicAck(tag, false);
        }
    }
}
