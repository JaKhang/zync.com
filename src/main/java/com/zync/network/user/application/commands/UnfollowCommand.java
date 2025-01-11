package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.domain.user.Relationship;

public record UnfollowCommand(ZID requesterId, ZID targetId) implements MediatorRequest<Relationship> {
}
