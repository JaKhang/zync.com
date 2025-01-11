package com.zync.network.core.domain;

public interface DomainEvent {
    default Class<? extends AggregateRoot> domainClass() {
        return AggregateRoot.class;
    }
}
