package com.zync.network.post.domain.events;

import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;

import java.util.List;
import java.util.Set;

public record PostUpdatedEvent(String content, List<ZID> mediaIds, Set<ZID> mentionIds) implements DomainEvent {
}
