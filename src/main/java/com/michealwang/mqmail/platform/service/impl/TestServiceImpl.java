package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.amqp.MessageHelper;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.json.ResponseCode;
import com.michealwang.mqmail.common.util.RandomUtil;
import com.michealwang.mqmail.config.mq.RabbitConfig;
import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
import com.michealwang.mqmail.platform.pojo.Mail;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import com.michealwang.mqmail.platform.service.TestService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 17:35
 * @Description
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private MsgLogMapper msgLogMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public JSONResponse testIdempotence() {
        return JSONResponse.success("delete success !");
    }

    @Override
    public JSONResponse testAccessLimit() {
        return JSONResponse.success("accessLimit: success !");
    }

    @Override
    public JSONResponse send(Mail mail) {
        String msgId = RandomUtil.UUID32();
        mail.setMsgId(msgId);

        MsgLog msgLog = new MsgLog(msgId, mail, RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);// 消息入库

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.MAIL_EXCHANGE_NAME, RabbitConfig.MAIL_ROUTING_KEY_NAME, MessageHelper.objToMsg(mail), correlationData);// 发送消息

        return JSONResponse.success(ResponseCode.MAIL_SEND_SUCCESS.getMsg());

    }
}
