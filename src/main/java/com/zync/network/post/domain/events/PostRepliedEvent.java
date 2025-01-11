package com.zync.network.post.domain.events;

import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;

import java.time.LocalDateTime;
import java.util.Set;

public record PostRepliedEvent(
        ZID authorId,
        ZID userId,
        ZID postId,
        Set<ZID> mentionIds,
        LocalDateTime at
) implements DomainEvent {
}
