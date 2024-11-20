package com.zync.network.account.application.queries;

import com.zync.network.core.mediator.Request;

public record CheckEmailQuery(String email) implements Request<Boolean> {
}
