package com.michealwang.mqmail.config.exception;

import com.michealwang.mqmail.common.json.JSONResponse;
import com.michealwang.mqmail.common.json.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/17 11:29
 * @Description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServiceException.class,IllegalArgumentException.class})
    public JSONResponse serviceExceptionHandler(RuntimeException e) {
        log.error("RuntimeException: " + e);
        return JSONResponse.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public JSONResponse exceptionHandler(Exception e) {
        log.error("Exception: ", e);
        return JSONResponse.error(ResponseCode.ERROR.getMsg());
    }
}
