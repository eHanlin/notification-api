package com.ehanlin.notification.message;

import com.ehanlin.notification.sender.Sender;

public abstract class BaseEmail implements Email {

    private String from = "news@ehanlinmail.com.tw";

    private String to;

    Sender<Email> sender;

    public BaseEmail() {
        // do nothing.
    }

    public BaseEmail(Sender<Email> sender) {
        this.sender = sender;
    }

    @Override
    public void send() {
        sender.send(this);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
