package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.domain.user.Relationship;

import java.util.List;
import java.util.Set;

public record SearchUsersQuery(String keyword, Set<Relationship> relationships, ZID selfId, int limit, int offset) implements MediatorRequest<List<UserPayload>> {

}
