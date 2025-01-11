package com.zync.network.post.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.post.application.payload.PostPayLoad;

import java.util.Set;

public interface PostClient {
    Set<PostPayLoad> findByIds(Set<ZID> postIds, ZID selfId);
}
