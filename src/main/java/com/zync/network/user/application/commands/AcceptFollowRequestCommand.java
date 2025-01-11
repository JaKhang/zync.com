package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

public record AcceptFollowRequestCommand(ZID requesterId, ZID targetId) implements MediatorNotification {
}
