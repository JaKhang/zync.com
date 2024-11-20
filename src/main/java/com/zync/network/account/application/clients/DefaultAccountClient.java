package com.zync.network.account.application.clients;

import com.zync.network.account.application.commands.RegisterCommand;
import com.zync.network.account.domain.aggregates.role.RoleName;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DefaultAccountClient implements AccountClient {

    Mediator mediator;

    @Override
    public ZID register(AccountRegisterRequest request) {
        return mediator.send(new RegisterCommand(request.email(), request.password(), Set.of(RoleName.USER)));
    }
}
