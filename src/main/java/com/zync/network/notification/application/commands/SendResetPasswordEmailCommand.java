package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.MediatorNotification;

public record SendResetPasswordEmailCommand(String email, String code, int age) implements MediatorNotification {
}
