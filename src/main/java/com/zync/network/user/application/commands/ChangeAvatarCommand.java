package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Notification;

public record ChangeAvatarCommand(ZID userId, ZID avatarId) implements Notification {
}
