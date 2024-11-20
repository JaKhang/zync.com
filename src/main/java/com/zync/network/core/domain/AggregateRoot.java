package com.zync.network.core.domain;


import jakarta.persistence.Transient;
import lombok.experimental.SuperBuilder;
import org.apache.commons.collections4.list.UnmodifiableList;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@SuperBuilder
public abstract class AggregateRoot extends AbstractEntity {
    @Transient
    private final Collection<DomainEvent> domainEvents ;

    public AggregateRoot(ZID id) {
        super(id);
        this.domainEvents = new HashSet<>();
    }

    @DomainEvents
    public Collection<DomainEvent> events() {
        return Collections.unmodifiableCollection(domainEvents);
    }

    @AfterDomainEventPublication
    public void onPublishedEvents(){
        domainEvents.clear();
    }



    public void registerEvents(DomainEvent domainEvent){
        domainEvents.add(domainEvent);
    }

    public AggregateRoot() {
        this.domainEvents =  new HashSet<>();
    }
}
