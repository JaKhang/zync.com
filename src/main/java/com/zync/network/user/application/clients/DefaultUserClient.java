package com.zync.network.user.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.application.queries.CheckFollowingQuery;
import com.zync.network.user.application.queries.FindFollowingIdsQuery;
import com.zync.network.user.application.queries.FindUserByIdQuery;
import com.zync.network.user.application.queries.FindUserByIdsQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DefaultUserClient implements UserClient{
    Mediator mediator;

    @Nullable
    @Override
    public UserPayload findById(ZID zid, ZID self) {
        return mediator.send(new FindUserByIdQuery(zid, self));
    }

    @Override
    public boolean isAccessAllowed(ZID userId, ZID selfId) {
        return false;
    }

    @Override
    public boolean isFollowing(ZID target, ZID self) {

        return mediator.send(new CheckFollowingQuery(target, self));
    }

    @Override
    public Set<ZID> findFollowingIds(ZID zid) {
        return mediator.send(new FindFollowingIdsQuery(zid));
    }

    @Override
    public Set<UserPayload> findByIds(Set<ZID> ids, ZID self) {
        return mediator.send(new FindUserByIdsQuery(ids, self));
    }
}
