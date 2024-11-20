package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.Notification;

public record SendResetPasswordEmailCommand(String email, String code, int age) implements Notification {
}
