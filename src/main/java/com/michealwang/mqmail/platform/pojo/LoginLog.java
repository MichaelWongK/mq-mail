package com.michealwang.mqmail.platform.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/21 10:46
 * @Description 登录日志
 */
@Data
public class LoginLog {

    @Id
    private String id;
    private Integer userId;
    private Integer type;
    private String description;
    private Date createTime;
    private Date updateTime;
    private String msgId;// 消息id

}
