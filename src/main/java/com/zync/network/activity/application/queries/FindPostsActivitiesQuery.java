package com.zync.network.activity.application.queries;

import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.activity.domain.ActivityType;

import java.util.List;
import java.util.Set;

public record FindPostsActivitiesQuery(ZID id, ZID selfId, int limit, int offset, Set<ActivityType> activities) implements MediatorRequest<List<ActivityPayload>> {
}
