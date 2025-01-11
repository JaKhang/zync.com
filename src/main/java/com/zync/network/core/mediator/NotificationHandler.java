package com.zync.network.core.mediator;

public interface NotificationHandler<T extends MediatorNotification> {
    void handle(T notification);
}
