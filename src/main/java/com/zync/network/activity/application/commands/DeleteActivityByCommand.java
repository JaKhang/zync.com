package com.zync.network.activity.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.activity.domain.ActivityType;

public record DeleteActivityByCommand(ZID actorId, ZID postId, ActivityType type) implements MediatorNotification {
}
