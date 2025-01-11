package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.MediatorNotification;

public record SendVerifyEmailCommand(String email, String code, int age) implements MediatorNotification {
}
