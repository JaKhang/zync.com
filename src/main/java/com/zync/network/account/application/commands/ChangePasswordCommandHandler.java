package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.events.PasswordChangedEvent;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class ChangePasswordCommandHandler implements NotificationHandler<ChangePasswordCommand> {

    PasswordEncoder passwordEncoder;
    AccountRepository accountRepository;

    @Override
    public void handle(ChangePasswordCommand notification) {
        Account account = accountRepository.findById(notification.id()).orElseThrow(() -> new ResourceNotFoundException("Account", "id", notification.id().toLowerCase()));
        account.changePassword(passwordEncoder.encode(notification.password()), passwordEncoder.encode(notification.newPassword()));
        accountRepository.save(account);
    }
}
