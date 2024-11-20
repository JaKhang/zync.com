package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.events.AccountVerifiedEvent;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Handler
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerifyCommandHandler implements NotificationHandler<VerifyCommand> {
    AccountRepository accountRepository;
    ApplicationEventPublisher eventPublisher;
    @Override
    public void handle(VerifyCommand command) {
        Account account = accountRepository.findByEmail(command.email()).orElseThrow(() -> new UsernameNotFoundException(""));
        account.verify(command.code());
        accountRepository.save(account);
    }
}
