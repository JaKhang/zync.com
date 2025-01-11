package com.zync.network.post.domain.events;

import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;

import java.time.LocalDateTime;
import java.util.Set;

public record PostRePostedEvent(ZID userId, ZID authorId, ZID postId, Set<ZID> mentionIds, LocalDateTime at) implements DomainEvent {
}
