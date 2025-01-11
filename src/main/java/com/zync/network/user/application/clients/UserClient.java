package com.zync.network.user.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.application.models.UserPayload;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface UserClient {
    @Nullable UserPayload findById(ZID zid, ZID selfId);

    boolean isAccessAllowed(ZID userId, ZID selfId);

    boolean isFollowing(ZID zid, ZID zid1);

    Set<ZID> findFollowingIds(ZID zid);

    Set<UserPayload> findByIds(Set<ZID> ids, ZID self);
}
