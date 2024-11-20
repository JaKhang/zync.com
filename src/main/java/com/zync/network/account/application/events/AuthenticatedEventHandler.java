package com.zync.network.account.application.events;


import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.aggregates.account.Device;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.events.AuthenticatedAnonymousDeviceEvent;
import com.zync.network.account.domain.events.AuthenticatedEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticatedEventHandler {

    AccountRepository accountRepository;

    @Async
    @EventListener
    @Transactional
    void handle(AuthenticatedEvent authenticatedEvent) {

    }
}
