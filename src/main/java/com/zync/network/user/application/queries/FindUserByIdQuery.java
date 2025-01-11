package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.UserPayload;

public record FindUserByIdQuery(ZID zid, ZID self) implements MediatorRequest<UserPayload> {
}
