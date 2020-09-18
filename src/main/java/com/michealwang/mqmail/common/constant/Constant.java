package com.michealwang.mqmail.common.constant;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 11:08
 * @Description
 */
public class Constant {

    public interface Redis {
        String OK = "OK";
        Integer EXPIRE_TIME_MINUTE = 60;// 过期时间, 60s, 一分钟
        Integer EXPIRE_TIME_HOUR = 60 * 60;// 过期时间, 一小时
        Integer EXPIRE_TIME_DAY = 60 * 60 * 24;// 过期时间, 一天
        String TOKEN_PREFIX = "token:";
        String MSG_CONSUMER_PREFIX = "consumer:";
        String ACCESS_LIMIT_PREFIX = "accessLimit:";
    }
}