package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.events.DevicesUnregisteredEvent;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class UnregisterDevicesCommandHandler implements NotificationHandler<UnregisterDevicesCommand> {
    AccountRepository accountRepository;
    ApplicationEventPublisher eventPublisher;
    @Override
    public void handle(UnregisterDevicesCommand notification) {
        log.debug("unregister device");
        Account account = accountRepository.findById(notification.accountId()).orElseThrow(() -> new ResourceNotFoundException("Account", "id", notification.accountId().toLowerCase()));
        account.unregisterDevices(notification.deviceIds());
        accountRepository.save(account);
    }
}
