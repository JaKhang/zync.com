package com.zync.network.core.mediator;

public interface Mediator {
    void send(MediatorNotification mediatorNotification);

    <T>T send(MediatorRequest<T> mediatorRequest);

}
