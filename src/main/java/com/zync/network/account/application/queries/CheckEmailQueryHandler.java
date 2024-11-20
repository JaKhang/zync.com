package com.zync.network.account.application.queries;

import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Handler
public class CheckEmailQueryHandler implements RequestHandler<CheckEmailQuery, Boolean> {
    AccountRepository accountRepository;
    @Override
    public Boolean handle(CheckEmailQuery request) {
        return accountRepository.existsByEmail(request.email());
    }
}
