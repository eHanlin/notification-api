package com.ehanlin.notification.sender;

public interface Sender<D> {

    void send(D obj);

}
