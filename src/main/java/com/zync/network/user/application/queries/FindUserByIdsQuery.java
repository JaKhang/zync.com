package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.UserPayload;

import java.util.Set;

public record FindUserByIdsQuery(Set<ZID> ids, ZID selfId) implements MediatorRequest<Set<UserPayload>> {

}
