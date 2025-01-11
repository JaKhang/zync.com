package com.zync.network.user.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.user.application.models.UserMapper;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.domain.user.Relationship;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.infrastructure.persistence.UserJpaRepository;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Handler
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class SearchUsersQueryHandler implements RequestHandler<SearchUsersQuery, List<UserPayload>> {
    UserJpaRepository repository;
    UserMapper mapper;

    @Override
    public List<UserPayload> handle(SearchUsersQuery request) {
        Pageable pageable = PageUtils.of(request.limit(), request.offset());

        Specification<User> specification = (root, query, builder) ->{

            return builder.and(
                    builder.or(
                            builder.like(root.get("username"), "%" + request.keyword() +"%"),
                            builder.like(root.get("fullName"), "%" + request.keyword() +"%")

                    ),
                    builder.notEqual(root.get("id"), request.selfId())
            );
        } ;

        if (request.relationships().contains(Relationship.FOLLOWING))
            specification = specification.and((root, query, builder) -> {
                return  builder.isMember(request.selfId(), root.get("followers"));
            });


        List<User> users = repository.findAll(specification, pageable).getContent();
        User self = repository.findById(request.selfId()).orElse(null);
        return users.stream().map(user -> mapper.toPayLoad(user, self)).toList();
    }
}
