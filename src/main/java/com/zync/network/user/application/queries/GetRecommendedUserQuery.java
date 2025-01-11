package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.UserPayload;

import java.util.List;

public record GetRecommendedUserQuery(ZID userId, Integer limit, Integer offset) implements MediatorRequest<List<UserPayload>> {
}
