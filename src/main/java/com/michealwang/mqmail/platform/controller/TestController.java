package com.michealwang.mqmail.platform.controller;

import com.michealwang.mqmail.common.util.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

//    @Autowired
//    private Re jedisUtil;
    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Autowired
    @Qualifier(value = "redisStringTemplate")
    private RedisTemplate StringRedisUtils;

    @RequestMapping("jedis")
    public String jedis() {

//        log.info(StringRedisUtils.opsForValue().set("aaa", "aaa"));
//
//        stringRedisUtils.set("aaa", "aaa");
//        stringRedisUtils.set("bbb", "bbb", 10);
//        log.info(stringRedisUtils.get("aaa"));
//
//        log.info(StringRedisUtils.setObject("ccc", "ccc"));
//        log.info(stringRedisUtils.setObject("ddd", "ddd", 30));
//        log.info(stringRedisUtils.getObject("ccc").toString());
//
//        log.info(stringRedisUtils.keys("ccc").toString());
//
//        log.info(stringRedisUtils.expire("aaa", 30) + "");
//        log.info(stringRedisUtils.ttl("aaa") + "");
//        log.info(stringRedisUtils.exists("aaa") + "");
//        log.info(stringRedisUtils.exists("aaaaaa") + "");

        return "hello world";
    }



}
