package com.zync.network.notification.domain;

import com.zync.network.core.domain.ZID;import com.zync.network.core.domain.AggregateRoot;

public abstract class SystemNotification extends AggregateRoot {
    ZID to;
    ZID from;
    boolean seen;
    boolean content;
    public SystemNotification() {
        super();
    }
}
