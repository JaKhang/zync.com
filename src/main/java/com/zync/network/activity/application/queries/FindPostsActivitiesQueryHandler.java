package com.zync.network.activity.application.queries;

import com.zync.network.activity.application.payload.ActivityMapper;
import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.infrastructure.repositories.ActivityJpaRepository;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.user.application.clients.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindPostsActivitiesQueryHandler implements RequestHandler<FindPostsActivitiesQuery, List<ActivityPayload>> {
    UserClient userClient;
    ActivityJpaRepository activityJpaRepository;
    ActivityMapper activityMapper;

    @Override
    public List<ActivityPayload> handle(FindPostsActivitiesQuery request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageUtils.of(request.limit(), request.offset(), sort);
        Specification<Activity> specification = (root, query, builder) -> builder.equal(
                root.get("postIs"),
                request.id()
        );

        List<Activity> activities = activityJpaRepository.findAll(specification, pageable).stream().toList();

        return activityMapper.map(activities, request.selfId());
    }
}
