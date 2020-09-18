package com.michealwang.mqmail.platform.service;

import com.michealwang.mqmail.common.json.JSONResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 14:36
 * @Description
 */
public interface TokenService {

    JSONResponse createToken();

    void checkToken(HttpServletRequest request);
}
