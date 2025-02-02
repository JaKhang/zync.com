package com.zync.network.account.application.commands;

import com.zync.network.core.domain.ZID;import com.zync.network.account.domain.aggregates.role.RoleName;
import com.zync.network.core.mediator.MediatorRequest;

import java.util.Set;

public record RegisterCommand(
        String username,
        String email,
        String password,
        Set<RoleName> roleNames
) implements MediatorRequest<ZID> {
}
