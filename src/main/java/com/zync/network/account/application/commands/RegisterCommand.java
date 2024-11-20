package com.zync.network.account.application.commands;

import com.zync.network.core.domain.ZID;import com.zync.network.account.domain.aggregates.role.RoleName;
import com.zync.network.core.mediator.Request;

import java.util.Locale;
import java.util.Set;

public record RegisterCommand(
        String email,
        String password,
        Set<RoleName> roleNames
) implements Request<ZID> {
}
