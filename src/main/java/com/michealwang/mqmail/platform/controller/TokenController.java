package com.michealwang.mqmail.platform.controller;

import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.platform.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 14:46
 * @Description
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("getToken")
    public JSONResponse getToken() {
        return tokenService.createToken();
    }
}
