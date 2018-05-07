package com.kevin.confcenter.admin.service.common;

import com.kevin.confcenter.common.bean.vo.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: kevin
 * @Description: mail测试
 * @Date: Created In 2018/5/5 18:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServieTest {

    @Autowired
    private MailServie mailServie;

    @Test
    public void sendMailTest() {
        Mail mail = new Mail();
        mail.setTo("conf_center@163.com");
        mail.setSubject("测试");
        mail.setText("这是一封自动发送的邮件哦");
        mail.setAttachmentPath("E:/picture/Penguins.jpg");
        mail.setAttachmentName("企鹅.jpg");
        mailServie.sendMail(mail);
    }
}
