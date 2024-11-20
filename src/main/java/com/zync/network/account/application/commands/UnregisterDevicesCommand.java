package com.zync.network.account.application.commands;

import com.zync.network.core.domain.ZID;import com.zync.network.core.mediator.Notification;

import java.util.Set;

public record UnregisterDevicesCommand(ZID accountId, Set<ZID> deviceIds) implements Notification {
}
