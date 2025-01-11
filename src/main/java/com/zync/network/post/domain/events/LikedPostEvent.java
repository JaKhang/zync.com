package com.zync.network.post.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Post;

import java.time.LocalDateTime;

public record LikedPostEvent(ZID postId, ZID userId, ZID authorId, LocalDateTime at) implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return Post.class;
    }
}
