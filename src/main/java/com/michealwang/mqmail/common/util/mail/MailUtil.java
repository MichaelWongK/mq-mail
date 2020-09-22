package com.michealwang.mqmail.common.util.mail;

import com.michealwang.mqmail.platform.pojo.Mail;
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
     * @param mail
     */
    public boolean send(Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(mail.getTo());
        simpleMailMessage.setSubject(mail.getTitle());
        simpleMailMessage.setText(mail.getContent());
        try {
            mailSender.send(simpleMailMessage);
            log.info("邮件发送成功, mail: {}", mail);
            return true;
        } catch (MailException e) {
            log.error("邮件发送失败, mail: {}", mail, e);
        }
        return false;
    }

    /**
     * 发送附件邮件
     *
     * @param mail
     * @param file    附件
     */
    public boolean send(Mail mail, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getTitle());
            helper.setText(mail.getContent());
            FileSystemResource resource = new FileSystemResource(file);
            String fileName = resource.getFilename();
            helper.addAttachment(fileName, resource);
            mailSender.send(message);
            log.info("附件邮件发送成功");
            return true;
        } catch (Exception e) {
            log.error("附件邮件发送失败, mail: {}", mail, e);
        }
        return false;
    }
}
