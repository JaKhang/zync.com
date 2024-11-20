package com.zync.network.core.domain;

public interface DomainEvent {
    Class<? extends AggregateRoot> domainClass();
}
