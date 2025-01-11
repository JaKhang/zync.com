package com.zync.network.account.application.queries;

import com.zync.network.core.mediator.MediatorRequest;

public record CheckEmailQuery(String email) implements MediatorRequest<Boolean> {
}
