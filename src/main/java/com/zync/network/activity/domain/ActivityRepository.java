package com.zync.network.activity.domain;

import com.zync.network.core.domain.DomainRepository;
import com.zync.network.core.domain.ZID;

import java.util.Collection;
import java.util.List;

public interface ActivityRepository extends DomainRepository<Activity> {
    Activity findByTypeAndActorIdAndPostId(ActivityType type, ZID actorId, ZID postId);

    List<Activity> findAllById(Collection<ZID> ids);
}
