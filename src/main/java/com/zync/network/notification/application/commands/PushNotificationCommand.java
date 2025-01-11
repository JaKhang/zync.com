package com.zync.network.notification.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

import java.time.LocalDateTime;

public record PushNotificationCommand(ZID actorId, ZID recipientId, ZID postId, String type, LocalDateTime at) implements MediatorNotification {
}
