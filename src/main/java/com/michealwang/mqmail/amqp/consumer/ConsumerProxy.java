package com.michealwang.mqmail.amqp.consumer;

import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 17:23
 * @Description consume 动态代理
 */
@Slf4j
public class ConsumerProxy {

    private Object target;
    private StringRedisUtils stringRedisUtils;

    public ConsumerProxy(Object target, StringRedisUtils stringRedisUtils) {
        this.target = target;
        this.stringRedisUtils = stringRedisUtils;
    }

    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces =  target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            Message message = (Message) args[0];
            // 保证消费幂等性, 避免消息被重复消费
            if (isConsumed(message)) {
                log.info("重复消费, correlationId: {}", getCorrelationId(message));
                return null;
            }
            Object result = method.invoke(target, args);
            return result;
        });

        return proxy;
    }

    /**
     * 消息是否已被消费
     * @param message
     * @return
     */
    private boolean isConsumed(Message message) {
        if (null == message) {
            return true;
        }

        String correlationId = getCorrelationId(message);
        if (StringUtils.isBlank(correlationId)) {
            return true;
        }

        boolean flag = stringRedisUtils.delete(Constant.Redis.MSG_CONSUMER_PREFIX + correlationId);
        if (!flag) {
            // 删除失败，说明已消费
            return true;
        }

        return false;

    }

    private String getCorrelationId(Message message) {
        String correlationId = "";
        MessageProperties properties = message.getMessageProperties();
        Map<String, Object> headers = properties.getHeaders();
        for (Map.Entry entry: headers.entrySet()) {
            String key = (String) entry.getKey();
            if (key.equals("spring_returned_message_correlation")) {
                correlationId = (String) entry.getValue();
            }
        }
        return correlationId;
    }
}
