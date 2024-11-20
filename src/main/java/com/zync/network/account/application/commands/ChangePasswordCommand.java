package com.zync.network.account.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Notification;

public record ChangePasswordCommand(ZID id, String password, String newPassword) implements Notification {
}
