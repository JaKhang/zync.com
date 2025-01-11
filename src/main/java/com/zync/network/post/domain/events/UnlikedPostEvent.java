package com.zync.network.post.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Post;

public record UnlikedPostEvent(ZID postId, ZID userId) implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return Post.class;
    }
}
