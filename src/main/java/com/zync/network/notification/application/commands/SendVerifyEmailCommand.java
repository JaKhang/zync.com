package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.Notification;

import java.util.Locale;

public record SendVerifyEmailCommand(String email, String code, int age) implements Notification {
}
