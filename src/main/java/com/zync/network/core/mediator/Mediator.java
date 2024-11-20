package com.zync.network.core.mediator;

public interface Mediator {
    void send(Notification notification);

    <T>T send(Request<T> request);

}
