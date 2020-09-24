package com.michealwang.mqmail.amqp.proxy;

import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import com.michealwang.mqmail.platform.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
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
public class BaseConsumerProxy {

    private Object target;
    private MsgLogService msgLogService;

    public BaseConsumerProxy(Object target, MsgLogService msgLogService) {
        this.target = target;
        this.msgLogService = msgLogService;
    }

    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces =  target.getClass().getInterfaces();

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            Message message = (Message) args[0];
            Channel channel = (Channel) args[1];

            String correlationId = getCorrelationId(message);
            // 保证消费幂等性, 避免消息被重复消费
            if (isConsumed(correlationId)) {
                log.info("重复消费, correlationId: {}", correlationId);
                return null;
            }

            Long tag = message.getMessageProperties().getDeliveryTag();

            try {
                Object result = method.invoke(target, args);
                // 更新消息状态为已消费
                msgLogService.updateStatus(correlationId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
                // 消费确认
                channel.basicAck(tag, false);
                return result;
            } catch (Exception e) {
                log.error(">>>>>getProxy error: ", e);
                channel.basicNack(tag, false, true);
                return null;
            }
        });

        return proxy;
    }

    /**
     * 消息是否已被消费
     * @param correlationId
     * @return
     */
    private boolean isConsumed(String correlationId) {
        MsgLog msgLog = msgLogService.selectByMsgId(correlationId);

        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {
            // 删除不存在或者消息已经是已消费状态，说明已消费
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
