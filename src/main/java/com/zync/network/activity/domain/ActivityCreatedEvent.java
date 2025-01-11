package com.zync.network.activity.domain;

import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;

import java.time.LocalDateTime;

public record ActivityCreatedEvent(
        ZID id,
        String type,
        ZID recipientId,
        ZID actorId,
        ZID postId,
        LocalDateTime at
) implements DomainEvent {
}
