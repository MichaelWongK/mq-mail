package com.michealwang.mqmail.platform.service;

import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.platform.pojo.Mail;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 17:34
 * @Description
 */
public interface TestService {

    JSONResponse testIdempotence();

    JSONResponse testAccessLimit();

    JSONResponse send(Mail mail);
}
