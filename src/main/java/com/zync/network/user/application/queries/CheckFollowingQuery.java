package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;

public record CheckFollowingQuery(ZID target, ZID self) implements MediatorRequest<Boolean> {
}
