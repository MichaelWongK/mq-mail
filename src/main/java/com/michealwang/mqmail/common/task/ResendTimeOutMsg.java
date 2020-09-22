package com.michealwang.mqmail.common.task;

import com.michealwang.mqmail.amqp.MessageHelper;
import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.util.JodaTimeUtil;
import com.michealwang.mqmail.common.util.JsonUtil;
import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
import com.michealwang.mqmail.platform.pojo.LoginLog;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 10:51
 * @Description
 */
@Component
@Slf4j
public class ResendTimeOutMsg {


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MsgLogMapper msgLogMapper;

    // 最大投递次数
    private static final int MAX_TRY_COUNT = 3;

//    @Scheduled(cron = "0/10 * * * * ?")
    public void ResendTimeOutMsg() {
        log.info("···定时任务ResendTimeOutMsg开始···");

        List<MsgLog> msgLogs = msgLogMapper.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT) {
                MsgLog mLog = new MsgLog();
                mLog.setMsgId(msgId);
                mLog.setStatus(Constant.MsgLogStatus.DELIVER_FAIL);
                msgLogMapper.updateStatus(mLog);

                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            } else {
                MsgLog mLog = new MsgLog();
                mLog.setMsgId(msgId);
                mLog.setNextTryTime(JodaTimeUtil.plusMinutes(msgLog.getNextTryTime(), 1));
                msgLogMapper.updateTryCount(mLog);

                // CorrelationData 对象，每个发送的消息都需要配备一个 CorrelationData 相关数据对象，
                // CorrelationData 对象内部只有一个 id 属性，用来表示当前消息唯一性。
                CorrelationData correlationData =new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(msgLog.getExchange(), msgLog.getRoutingKey(), MessageHelper.objToMsg(msgLog.getMsg()), correlationData);

                log.info("第 " + (msgLog.getTryCount() + 1) + " 次重新投递消息");
            }

            log.info("定时任务ResendTimeOutMsg结束");
        });
    }
}
