package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.application.models.UserPayload;

import java.util.List;

public record GetRequestedUsersQuery(ZID selfId,ZID userId, int limit, int offset) implements MediatorRequest<List<UserPayload>> {
}
