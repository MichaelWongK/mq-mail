package com.michealwang.mqmail.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.michealwang.mqmail.platform.mapper.LoginLogMapper;
import com.michealwang.mqmail.platform.pojo.LoginLog;
import com.michealwang.mqmail.platform.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 10:50
 * @Description
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insert(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    @Override
    public LoginLog selectByMsgId(String msgId) {
        return this.getOne(new QueryWrapper<LoginLog>().eq("msg_id", msgId));
    }
}
