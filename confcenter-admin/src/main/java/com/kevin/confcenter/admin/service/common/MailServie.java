package com.kevin.confcenter.admin.service.common;

import com.kevin.confcenter.common.bean.vo.Mail;

/**
 * @Author: kevin
 * @Description: 邮件service
 * @Date: Created In 2018/5/5 17:29
 */
public interface MailServie {

    /**
     * 发送邮件
     *
     * @param mail
     */
    void sendMail(Mail mail);
}
