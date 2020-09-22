package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.amqp.consumer.MessageHelper;
import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.json.ResponseCode;
import com.michealwang.mqmail.common.util.*;
import com.michealwang.mqmail.platform.mapper.MsgLogMapper;
import com.michealwang.mqmail.platform.mapper.UserMapper;
import com.michealwang.mqmail.platform.pojo.LoginLog;
import com.michealwang.mqmail.platform.pojo.MsgLog;
import com.michealwang.mqmail.platform.pojo.User;
import com.michealwang.mqmail.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MsgLogMapper msgLogMapper;
    @Autowired
    private StringRedisUtils stringRedisUtils;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    // 直连交换机
    @Value("${log.login.directexchange}")
    private   String loginLogExchange;
    // 日志路由
    @Value("${log.login.routing}")
    private String loginLogRoutingKey;

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.selectOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public JSONResponse login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return JSONResponse.error(ResponseCode.USERNAME_OR_PASSWORD_EMPTY.getMsg());
        }

        User user = getByUsernameAndPassword(username, password);
        Assert.notNull(user, ResponseCode.USER_NOT_EXISTS.getMsg());

        saveAndSendMsg(user);

        return JSONResponse.success();
    }

    /**
     * 保存并发送消息
     * @param user
     */
    private void saveAndSendMsg(User user) {
        LoginLog loginLog = new LoginLog();
        String msgId = RandomUtil.UUID32();
        // 发送消息
        loginLog.setUserId(user.getId());
        loginLog.setType(Constant.LogType.LOGIN);
        Date date = new Date();
        loginLog.setDescription(user.getUsername() + "在" + JodaTimeUtil.dateToStr(date) + "登录系统");
        loginLog.setCreateTime(date);
        loginLog.setUpdateTime(date);
        loginLog.setMsgId(msgId);

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(msgId);
        rabbitTemplate.convertAndSend(loginLogExchange, loginLogRoutingKey, loginLog, correlationData);

        MsgLog msgLog = new MsgLog();
        msgLog.setMsgId(msgId);
        msgLog.setMsg(JsonUtil.objToStr(loginLog));
        msgLog.setExchange(loginLogExchange);
        msgLog.setRoutingKey(loginLogRoutingKey);
        msgLog.setStatus(Constant.MsgLogStatus.SENDING);
        msgLog.setTryCount(0);
        msgLog.setCreateTime(date);
        msgLog.setUpdateTime(date);
        msgLog.setNextTryTime(JodaTimeUtil.plusMinutes(date, 1));
        msgLogMapper.insert(msgLog);

    }


}
