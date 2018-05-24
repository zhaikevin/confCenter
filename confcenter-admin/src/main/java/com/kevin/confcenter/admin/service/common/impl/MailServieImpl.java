package com.kevin.confcenter.admin.service.common.impl;

import com.kevin.confcenter.admin.service.common.MailServie;
import com.kevin.confcenter.common.bean.vo.Mail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Author: kevin
 * @Description: 邮件service实现
 * @Date: Created In 2018/5/5 17:38
 */
@Service(value = "mailService")
public class MailServieImpl implements MailServie {

    /**
     * 日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServieImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String Sender;

    @Override
    public void sendMail(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(Sender);
            helper.setTo(mail.getTo());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            if (StringUtils.isNotEmpty(mail.getAttachmentPath())
                    && StringUtils.isNotEmpty(mail.getAttachmentName())) {
                FileSystemResource file = new FileSystemResource(new File(mail.getAttachmentPath()));
                helper.addAttachment(mail.getAttachmentName(), file);
            }
            mailSender.send(message);
        } catch (Exception e) {
            LOGGER.error("send mail failed,exception message:{}", e.getMessage(), e);
        }
    }
}
