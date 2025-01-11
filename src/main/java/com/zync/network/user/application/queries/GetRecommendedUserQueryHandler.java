package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.application.models.UserMapper;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.infrastructure.persistence.UserJpaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GetRecommendedUserQueryHandler implements RequestHandler<GetRecommendedUserQuery, List<UserPayload>> {

    UserJpaRepository userJpaRepository;
    UserMapper mapper;

    @Override
    public List<UserPayload> handle(GetRecommendedUserQuery request) {
        User self = userJpaRepository.findById(request.userId()).orElseThrow(() -> new ResourceNotFoundException("USER", "id", request.userId()));

        Specification<User> specification = notFollow(request.userId());
        Pageable pageable = PageRequest.of(request.offset() / request.limit(), request.limit());
        Page<User> users = userJpaRepository.findAll(specification, pageable);
        return users.map(user -> mapper.toPayLoad(user, self)).getContent();
    }

    private Specification<User> notFollow(ZID zid) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.isNotMember(zid, root.get("followers")),
                    criteriaBuilder.notEqual(root.get("id"), zid)
            );
        };
    }
}
