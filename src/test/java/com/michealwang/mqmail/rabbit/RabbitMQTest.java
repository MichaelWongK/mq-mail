package com.michealwang.mqmail.rabbit;

import com.michealwang.mqmail.platform.pojo.LoginLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 15:51
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 直连交换机
    @Value("${log.login.directexchange}")
    private   String loginLogExchange;
    // 日志路由
    @Value("${log.login.routing}")
    private String loginLogRoutingKey;

    @Test
    public void testProducer() {
        LoginLog loginLog = new LoginLog();
        loginLog.setDescription(">>>>>>>>>>test<<<<<<<<<");
        rabbitTemplate.convertAndSend(loginLogExchange, loginLogRoutingKey);
    }
}
