package com.michealwang.mqmail.common.util.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author <a href="mailto:wangmk13@163.com">micheal.wang</a>
 * @date 2020/9/22 21:07
 * @Description
 */
@Component
@Slf4j
public class MailUtil {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    /**
     *  发送简单邮件
     *
     * @param to        目标邮箱
     * @param title     邮件标题
     * @param content   邮件正文
     */
    public void send(String to, String title, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        try {
            mailSender.send(simpleMailMessage);
            log.info("邮件发送成功, to: {}, title：{}", to, title);
        } catch (MailException e) {
            log.error("邮件发送失败, to: {}, title: {}", to, title, e);
        }
    }

    /**
     * 发送附件邮件
     *
     * @param to
     * @param title
     * @param content
     * @param file    附件
     */
    public void send(String to, String title, String content, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content);
            FileSystemResource resource = new FileSystemResource(file);
            String fileName = resource.getFilename();
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
            log.info("附件邮件发送成功");
        } catch (Exception e) {
            log.error("附件邮件发送失败, to: {}, title: {}", to, title, e);
        }
    }
}
