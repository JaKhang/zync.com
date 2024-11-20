package com.zync.network.account.domain.events;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;

public record AccountCreatedEvent() implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return Account.class;
    }
}
