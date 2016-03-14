package com.ehanlin.notification.message;

import com.ehanlin.notification.sender.Sender;
import com.ehanlin.notification.template.annotation.TemplateBy;

/**
 * 修改email帳號
 * @author
 */
@TemplateBy("template/editEmail/email")
public class EditEmailEmail extends BaseEmail {

    @TemplateBy
    private String subject;

    @TemplateBy
    private String text;

    public EditEmailEmail() {
        // do nothing.
    }

    public EditEmailEmail(Sender<Email> sender) {
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
