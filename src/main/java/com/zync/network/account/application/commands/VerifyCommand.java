package com.zync.network.account.application.commands;

import com.zync.network.core.mediator.Notification;

public record VerifyCommand(
        String email,
        String code
) implements Notification {
}
