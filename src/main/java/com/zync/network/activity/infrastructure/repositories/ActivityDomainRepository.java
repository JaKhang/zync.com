package com.zync.network.activity.infrastructure.repositories;

import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.domain.ActivityRepository;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.activity.domain.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ActivityDomainRepository implements ActivityRepository {

    private final ActivityJpaRepository repository;
    @Override
    public Optional<Activity> findById(ZID id) {
        // TODO (PC, 31/12/2024): To change the body of an implemented method
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(Activity activity) {
        return repository.save(activity).getId();
    }

    @Override
    public Activity findByTypeAndActorIdAndPostId(ActivityType type, ZID actorId, ZID postId) {
        Page<Activity> activities = repository.findAll((root, query, builder) -> builder.and(
                builder.equal(root.get("type"), type),
                builder.equal(root.get("actorId"), actorId),
                builder.equal(root.get("postId"), postId)
        ), Pageable.ofSize(1));
        if (activities.isEmpty()) throw new ResourceNotFoundException("Activity", "type,actorId,postId", List.of(type, actorId, postId));
        return activities.getContent().getFirst();
    }

    @Override
    public List<Activity> findAllById(Collection<ZID> ids) {
        return repository.findAllById(ids);
    }
}
