package com.michealwang.mqmail.platform.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 10:39
 * @Description
 */
@Data
public class MsgLog {

    private String msgId;
    private String msg;
    private String exchange;
    private String routingKey;
    private Integer status;
    private Integer tryCount;
    private Date nextTryTime;
    private Date createTime;
    private Date updateTime;
}
