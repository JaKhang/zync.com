package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LogoutCommandHandler implements NotificationHandler<LogoutCommand> {
    AccountRepository accountRepository;

    @Override
    public void handle(LogoutCommand notification) {
        Account account = accountRepository.findById(notification.accountId()).orElseThrow(() -> new ResourceNotFoundException("account", "id", notification.accountId().toLowerCase()));
        account.unregisterDevice(notification.accountId());
        accountRepository.save(account);
    }
}
