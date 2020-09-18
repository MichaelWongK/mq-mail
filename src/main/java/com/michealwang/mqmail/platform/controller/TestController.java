package com.michealwang.mqmail.platform.controller;

import com.michealwang.mqmail.common.annotation.AccessLimit;
import com.michealwang.mqmail.common.annotation.ApiIdempotent;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.michealwang.mqmail.platform.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Autowired
    @Qualifier(value = "redisStringTemplate")
    private RedisTemplate StringRedisUtils;

    @Autowired
    private TestService testService;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/testIdempotence")
    @ApiIdempotent
    public JSONResponse testIdempotence(String token) {
        return testService.testIdempotence();
    }

    @RequestMapping("/testAccessLimit")
    @AccessLimit(maxCount = 10, seconds = 200)
    public JSONResponse testAccessLimit() {
        return testService.testAccessLimit();
    }

    @GetMapping("/testRabbitMq")
    public JSONResponse testRabbitMq() {
        for (int i=0; i<10; i++) {
            rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_EXCHANGE_NAME, RabbitConfig.LOG_ROUTING_KEY_NAME, "log msg is " + i);
        }
        return JSONResponse.success("mq send success");
    }


}
