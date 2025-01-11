package com.zync.network.activity.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.activity.domain.ActivityType;

import java.time.LocalDateTime;

public record CreateActivityCommand(
        ZID actorId,
        ZID targetId,
        ZID postId,
        LocalDateTime at,
        ActivityType type
) implements MediatorRequest<ZID> {
}
