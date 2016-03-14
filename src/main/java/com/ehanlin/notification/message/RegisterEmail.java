package com.ehanlin.notification.message;

import com.ehanlin.notification.sender.Sender;
import com.ehanlin.notification.template.annotation.TemplateBy;

@TemplateBy("template/register/email")
public class RegisterEmail extends BaseEmail {

    @TemplateBy
    private String subject;

    @TemplateBy
    private String text;

    public RegisterEmail() {
        // do nothing.
    }

    public RegisterEmail(Sender<Email> sender) {
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
