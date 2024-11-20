package com.zync.network.media.domain.events;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.DomainEvent;
import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Image;

public record VariantCreatedEvent(ZID id, String reference, int width, int height) implements DomainEvent {
    @Override
    public Class<? extends AggregateRoot> domainClass() {
        return Image.class;
    }
}
