package com.zync.network.user.application.commands;

import com.zync.network.account.application.clients.AccountClient;
import com.zync.network.account.application.clients.AccountRequest;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserFactory;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RegisterUserCommandHandler implements RequestHandler<RegisterUserCommand, ZID> {

    AccountClient accountClient;
    UserRepository userRepository;

    @Override
    @Transactional
    public ZID handle(RegisterUserCommand request) {
        ZID accountId = accountClient.register(new AccountRequest(request.username(), request.email(), request.password()));
        User user = UserFactory.crate(accountId, request.username(), request.firstName(), request.lastName(), request.middleName(), request.dateOfBirth(), request.locale(), request.gender());
        return userRepository.save(user);
    }
}
