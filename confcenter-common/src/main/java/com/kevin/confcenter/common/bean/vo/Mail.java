package com.kevin.confcenter.common.bean.vo;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: kevin
 * @Description: 邮件实体类
 * @Date: Created In 2018/5/5 17:31
 */
public class Mail {

    /**
     * 收件人的邮箱，多个邮箱以,隔开
     */
    private String to;

    /**
     * 主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    /**
     * 邮件附件路径
     */
    private String attachmentPath;

    /**
     * 邮件附件名称
     */
    private String attachmentName;

    public String[] getTo() {
        if (StringUtils.isEmpty(to)) {
            return null;
        }
        String[] toS = to.split(",");
        return toS;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachmentPath='" + attachmentPath + '\'' +
                ", attachmentName='" + attachmentName + '\'' +
                '}';
    }
}
