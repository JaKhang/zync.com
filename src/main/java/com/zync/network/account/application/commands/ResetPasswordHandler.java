package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.events.PasswordResetEvent;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ResetPasswordHandler implements NotificationHandler<ResetPasswordCommand> {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;
    ApplicationEventPublisher eventPublisher;
    @Override
    public void handle(ResetPasswordCommand notification) {
        Account account = accountRepository.findByUsernameOrEmail(notification.email()).orElseThrow(() -> new ResourceNotFoundException("Account", "usernameOrEmail", notification.email()));
        account.restPassword(notification.code(), passwordEncoder.encode(notification.newPassword()));
        accountRepository.save(account);
        eventPublisher.publishEvent(new PasswordResetEvent(notification.email(), notification.code(), notification.newPassword()));
    }
}
