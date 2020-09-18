package com.michealwang.mqmail.common.interceptor;

import com.michealwang.mqmail.common.annotation.AccessLimit;
import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.json.ResponseCode;
import com.michealwang.mqmail.common.util.IpUtil;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.config.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 16:42
 * @Description
 */
public class AccessLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        AccessLimit annotation = method.getAnnotation(AccessLimit.class);
        if (null != annotation) {
            accessCheck(annotation, request);
        }
        return true;
    }

    private void accessCheck(AccessLimit annotation, HttpServletRequest request) {
        int maxCount = annotation.maxCount();
        int seconds = annotation.seconds();

        StringBuilder sb = new StringBuilder();
        // 根据ip+requestUri构成key
        String key = sb.append(Constant.Redis.ACCESS_LIMIT_PREFIX).append(IpUtil.getIpAddress(request)).append(request.getRequestURI()).toString();
        // 判断redis中是否存在key值
        boolean exits = stringRedisUtils.hasKey(key);
        if (!exits) {
            // 不存在则存入redis
            stringRedisUtils.setEx(key, String.valueOf(1), seconds, TimeUnit.SECONDS);
        } else {
            // 存在则取出count与限定最大值值比较
            int count = Integer.valueOf(stringRedisUtils.get(key));
            if (count >= maxCount) {
                // 超出最大次数
                throw new ServiceException(ResponseCode.ACCESS_LIMIT.getMsg());
            }
            // key剩余过期时间
            Long ttl = stringRedisUtils.getExpire(key);
            if (ttl <= 0) {
                stringRedisUtils.setEx(key, String.valueOf(1), seconds, TimeUnit.SECONDS);
            } else {
                stringRedisUtils.setEx(key, String.valueOf(++count), ttl, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
