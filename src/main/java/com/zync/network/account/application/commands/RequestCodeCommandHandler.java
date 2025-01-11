package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.infrastructure.generator.CodeGenerator;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.domain.events.ResetPasswordCodeCreatedEvent;
import com.zync.network.account.domain.events.TwoStepCodeCreatedEvent;
import com.zync.network.account.domain.events.VerifyCodeCreatedEvent;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class RequestCodeCommandHandler implements RequestHandler<RequestCodeCommand, Boolean> {

    AccountRepository accountRepository;
    CodeGenerator codeGenerator;
    ApplicationEventPublisher eventPublisher;

    @Override
    public Boolean handle(RequestCodeCommand command) {
        Account account = accountRepository.findByEmail(command.email()).orElseThrow(() -> new UsernameNotFoundException("not found actor"));
        String code = codeGenerator.generate();
        account.addCode(code, 5, command.codeType());
        accountRepository.save(account);

        return true;
    }
}
