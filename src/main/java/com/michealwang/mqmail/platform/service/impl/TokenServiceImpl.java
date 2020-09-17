package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.util.RandomUtil;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.platform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 14:38
 * @Description 创建token
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private StringRedisUtils stringRedisUtils;

    @Override
    public JSONResponse createToken() {
        String randomStr = RandomUtil.generateStr(24);
        StringBuilder token = new StringBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(randomStr);

        stringRedisUtils.setEx(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_MINUTE, TimeUnit.MINUTES);
        return JSONResponse.success(token);
    }
}
