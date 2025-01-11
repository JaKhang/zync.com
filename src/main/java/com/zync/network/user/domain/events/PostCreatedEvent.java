package com.zync.network.user.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;

import java.time.LocalDateTime;
import java.util.Set;

public record PostCreatedEvent(ZID postId, Set<ZID> mentionIds, ZID authorId, String type,
                               ZID referencePostId, ZID threadId, LocalDateTime at) implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        // TODO (PC, 25/12/2024): To change the body of an implemented method
        return null;
    }
}
