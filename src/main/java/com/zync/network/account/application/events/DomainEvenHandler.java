package com.zync.network.account.application.events;

import com.zync.network.account.domain.events.AccountCreatedEvent;
import com.zync.network.core.mediator.Handler;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.modulith.events.ApplicationModuleListener;

@Handler
public class DomainEvenHandler {
    @ApplicationModuleListener
    @DomainEventHandler
    public void on(AccountCreatedEvent event){

    }
}
