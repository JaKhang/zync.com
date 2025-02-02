package com.zync.network.account.application.commands;

import com.zync.network.account.domain.aggregates.account.CodeType;
import com.zync.network.core.mediator.MediatorRequest;

public record RequestCodeCommand(String email, CodeType codeType) implements MediatorRequest<Boolean> {
}
