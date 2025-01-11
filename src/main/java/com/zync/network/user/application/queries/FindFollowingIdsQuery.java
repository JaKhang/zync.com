package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;

import java.util.Set;

public record FindFollowingIdsQuery(ZID id) implements MediatorRequest<Set<ZID>> {
}
