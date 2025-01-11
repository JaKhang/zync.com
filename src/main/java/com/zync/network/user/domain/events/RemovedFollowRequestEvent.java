package com.zync.network.user.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.User;

public record RemovedFollowRequestEvent(ZID id, ZID id1) implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return User.class;
    }
}
