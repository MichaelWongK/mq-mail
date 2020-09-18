package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.common.constant.Constant;
import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.json.ResponseCode;
import com.michealwang.mqmail.common.util.RandomUtil;
import com.michealwang.mqmail.common.util.StringRedisUtils;
import com.michealwang.mqmail.platform.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 14:38
 * @Description 创建token
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

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

    @Override
    public void checkToken(HttpServletRequest request) {
        // 验证请求参数是否携带token
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            Assert.notNull(token, ResponseCode.ILLEGAL_ARGUMENT.getMsg());
        }
        // 验证redis中是否存在该token,不存在说明重复提交
        Assert.isTrue(stringRedisUtils.hasKey(token), ResponseCode.REPETITIVE_OPERATION.getMsg());
        // 验证redis中token是否删除成功，成功说明是首次提交，失败说明重复提交，不再往下执行，直接抛出异常
        Assert.isTrue(stringRedisUtils.delete(token), ResponseCode.REPETITIVE_OPERATION.getMsg());
    }
}
