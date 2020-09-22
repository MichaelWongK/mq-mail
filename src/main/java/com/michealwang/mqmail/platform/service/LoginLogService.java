package com.michealwang.mqmail.platform.service;

import com.michealwang.mqmail.platform.pojo.LoginLog;

import java.util.List;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 10:43
 * @Description
 */
public interface LoginLogService {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);
}
