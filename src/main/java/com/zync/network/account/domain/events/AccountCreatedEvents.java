package com.zync.network.account.domain.events;

import com.zync.network.core.domain.ZID;import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.aggregates.role.Role;
import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;

import java.util.Locale;
import java.util.Set;

public record AccountCreatedEvents(
        ZID id,
        String email,
        String password,
        Set<Role> roles
)implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return Account.class;
    }
}