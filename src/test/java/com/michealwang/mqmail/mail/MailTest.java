package com.michealwang.mqmail.mail;

import com.michealwang.mqmail.common.util.mail.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 21:25
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailUtil mailUtil;

    @Test
    public void send() {
//        mailUtil.send("912570281@qq.com", "日志标题", "邮件正文内容哦哦哦哦哦哦哦哦哦");
    }

    @Test
    public void sendAttachment() {
        File file = new File("C:/Users/wangm/Desktop/正则思维导图.jpg");
//        mailUtil.send("912570281@qq.com", "日志标题", "邮件正文内容哦哦哦哦哦哦哦哦哦", file);
    }

}
