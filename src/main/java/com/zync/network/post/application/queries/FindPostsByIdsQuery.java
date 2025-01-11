package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.post.application.payload.PostPayLoad;

import java.util.Set;

public record FindPostsByIdsQuery(Set<ZID> postIds, ZID selfId) implements MediatorRequest<Set<PostPayLoad>> {
}
