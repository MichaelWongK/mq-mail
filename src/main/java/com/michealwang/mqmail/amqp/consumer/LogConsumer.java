package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.config.mq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 23:11
 * @Description
 */
@Configuration
@Slf4j
public class LogConsumer {

    @RabbitListener(queues = RabbitConfig.LOG_QUEUE_NAME)
    public void conusmerLog(String msg) {
        log.info("conusmerLog1 消费日志信息：{}", msg);
    }

    @RabbitListener(queues = RabbitConfig.LOG_QUEUE_NAME)
    public void conusmerLog2(String msg) {
        log.info("conusmerLog2 消费日志信息：{}", msg);
    }
}
