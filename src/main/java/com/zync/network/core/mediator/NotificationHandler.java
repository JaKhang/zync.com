package com.zync.network.core.mediator;

public interface NotificationHandler<T extends Notification> {
    void handle(T notification);
}
