package com.zync.network.account.application.commands;

import com.zync.network.core.mediator.Notification;

public record ResetPasswordCommand(String email, String code, String newPassword) implements Notification {
}
