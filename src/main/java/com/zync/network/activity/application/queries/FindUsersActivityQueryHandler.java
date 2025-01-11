package com.zync.network.activity.application.queries;

import com.zync.network.activity.application.payload.ActivityMapper;
import com.zync.network.activity.application.payload.ActivityPayload;
import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.infrastructure.repositories.ActivityJpaRepository;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindUsersActivityQueryHandler implements RequestHandler<FindUsersActivitiesQuery, List<ActivityPayload>> {
    ActivityMapper mapper;
    ActivityJpaRepository repository;

    @Override
    public List<ActivityPayload> handle(FindUsersActivitiesQuery request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageUtils.of(request.limit(), request.offset(), sort);

        Specification<Activity> specification = (root, query, builder) -> {
            if (request.types().isEmpty()) return builder.equal(root.get("deleted"), false);
            return builder.and(
                    builder.equal(root.get("deleted"), false),
                    builder.equal(root.get("recipientId"), request.userId())
            );
        };

        List<Activity> activities = repository.findAll(specification, pageable).toList();

        return mapper.map(activities, request.userId());
    }
}
