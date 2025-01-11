package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.UserPayload;

import java.util.List;

public record GetFollowingsQuery(ZID selfId,ZID userId, int limit, int offset) implements MediatorRequest<List<UserPayload>> {
}
