package com.zync.network.account.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

public record LogoutCommand(ZID accountId, ZID deviceId) implements MediatorNotification {
}
