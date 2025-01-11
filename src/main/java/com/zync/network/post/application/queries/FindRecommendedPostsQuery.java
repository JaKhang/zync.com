package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.post.application.payload.PostPayLoad;

import java.util.List;

public record FindRecommendedPostsQuery(ZID self, int limit, int offset) implements MediatorRequest<List<PostPayLoad>> {
}
