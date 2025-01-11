package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.PostType;

import java.util.List;
import java.util.Set;

public record FindPostsByAuthorIdQuery(
        ZID userId,
        ZID sefId,
        int limit,
        int offset,
        Set<PostType> types
) implements MediatorRequest<List<PostPayLoad>> {
}
