package com.michealwang.mqmail.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 16:30
 * @Description 防刷限流
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    // 固定 seconds 内最大访问次数
    int maxCount();
    // 固定时间 单位：s
    int seconds();
}
