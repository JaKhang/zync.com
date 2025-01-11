package com.zync.network.activity.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

import java.util.Set;

public record SeenActivitiesCommand(Set<ZID> ids) implements MediatorNotification {
}
