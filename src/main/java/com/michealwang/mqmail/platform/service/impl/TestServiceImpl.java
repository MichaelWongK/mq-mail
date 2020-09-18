package com.michealwang.mqmail.platform.service.impl;

import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.platform.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/18 17:35
 * @Description
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public JSONResponse testIdempotence() {
        return JSONResponse.success("delete success !");
    }

    @Override
    public JSONResponse testAccessLimit() {
        return JSONResponse.success("accessLimit: success !");
    }
}
