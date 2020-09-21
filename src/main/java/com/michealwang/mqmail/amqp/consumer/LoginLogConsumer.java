package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.common.util.RandomUtil;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.michealwang.mqmail.platform.pojo.LoginLog;
import com.michealwang.mqmail.platform.service.LoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 14:02
 * @Description 登录日志消费
 */
@Slf4j
@Component
public class LoginLogConsumer {

    @Autowired
    private LoginLogService loginLogService;

    @RabbitListener(queues = "log.login.queue")
    public void LoginLogConsumer(LoginLog loginLog) {
        log.info("loginLog1 消费消息：{}", loginLog);
        loginLog.setId(RandomUtil.generateDigitalStr(5));
        loginLogService.insert(loginLog);
    }
}
