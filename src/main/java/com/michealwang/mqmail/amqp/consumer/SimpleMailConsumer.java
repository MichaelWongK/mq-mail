//package com.michealwang.mqmail.amqp.consumer;
//
//import com.michealwang.mqmail.amqp.MessageHelper;
//import com.michealwang.mqmail.common.constant.Constant;
//import com.michealwang.mqmail.common.util.mail.MailUtil;
//import com.michealwang.mqmail.config.mq.RabbitConfig;
//import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
//import com.michealwang.mqmail.platform.pojo.Mail;
//import com.michealwang.mqmail.platform.pojo.MsgLog;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
///**
// * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
// * @date 2020/9/22 22:03
// * @Description
// */
//@Slf4j
//@Component
//public class SimpleMailConsumer {
//
//    @Autowired
//    private MsgLogMapper msgLogMapper;
//    @Autowired
//    private MailUtil mailUtil;
//
//    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
//    public void consume(Message message, Channel channel) throws IOException {
//
//        Mail mail = MessageHelper.msgToObj(message, Mail.class);
//        log.info("收到消息: {}", mail.toString());
//
//        String msgId = mail.getMsgId();
//
//        MsgLog msgLog = msgLogMapper.selectByPrimaryKey(msgId);
//        // 消费幂等性
//        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {
//            log.info("重复消费, msgId: {}", msgId);
//            return;
//        }
//
//        long tag = message.getMessageProperties().getDeliveryTag();
//        boolean flag = mailUtil.send(mail);
//        if (flag) {
//            MsgLog msgLog1 = new MsgLog();
//            msgLog1.setMsgId(msgId);
//            msgLog1.setStatus(Constant.MsgLogStatus.CONSUMED_SUCCESS);
//            msgLogMapper.updateStatus(msgLog1);
//            channel.basicAck(tag, false);// 消费确认
//        } else {
//            channel.basicNack(tag, false, true);
//        }
//    }
//}
