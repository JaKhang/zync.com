package com.zync.network.account.application.commands;

import com.zync.network.core.mediator.MediatorNotification;

public record VerifyCommand(
        String email,
        String code
) implements MediatorNotification {
}
