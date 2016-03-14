package com.ehanlin.notification.message;

/**
 * <h1>完全無加工的郵件模型</h1>
 */
public class BlankJavaMail implements Email {

    private String subject;

    private String text;

    private String from;

    private String to;

    public BlankJavaMail() {
        // for create blank one.
    }

    public BlankJavaMail(String subject, String text, String from, String to) {
        this.subject = subject;
        this.text = text;
        this.from = from;
        this.to = to;
    }

    @Override
    public void send() {
        // Unsupported operation!
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }

}
