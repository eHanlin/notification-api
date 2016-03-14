package com.ehanlin.notification.message;

import com.ehanlin.notification.sender.Sender;
import com.ehanlin.notification.template.annotation.TemplateBy;

/**
 * 密碼重設信
 * @author rodick_huang
 */
@TemplateBy("template/referral/email")
public class ReferralEmail extends BaseEmail {

    @TemplateBy
    private String subject;

    @TemplateBy
    private String text;

    public ReferralEmail() {
        // do nothing.
    }

    public ReferralEmail(Sender<Email> sender) {
        super(sender);
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

}
