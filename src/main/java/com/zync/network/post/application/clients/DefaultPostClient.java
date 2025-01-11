package com.zync.network.post.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.application.queries.FindPostsByIdsQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DefaultPostClient implements PostClient{
    Mediator mediator;
    @Override
    public Set<PostPayLoad> findByIds(Set<ZID> postIds, ZID selfId) {
        // TODO (PC, 01/01/2025): To change the body of an implemented method
        return mediator.send(new FindPostsByIdsQuery(postIds, selfId));
    }
}
