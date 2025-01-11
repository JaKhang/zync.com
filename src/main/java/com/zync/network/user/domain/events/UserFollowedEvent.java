package com.zync.network.user.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.User;

import java.time.LocalDateTime;

public record UserFollowedEvent(ZID userId, ZID follower, LocalDateTime at)  implements DomainEvent {


    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return User.class;
    }
}
