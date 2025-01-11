package com.zync.network.user.application.queries;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.user.application.models.ProfileMapper;
import com.zync.network.user.application.models.ProfilePayload;
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
import org.springframework.data.jpa.support.PageableUtils;

import java.util.List;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GetFollowersQueryHandler implements RequestHandler<GetFollowersQuery, List<UserPayload>> {

    UserJpaRepository userJpaRepository;
    UserMapper mapper;

    @Override
    public List<UserPayload> handle(GetFollowersQuery request) {
        User self = userJpaRepository.findById(request.selfId()).orElseThrow(() -> new ResourceNotFoundException("USER", "id", request.selfId()));
        Specification<User> specification = (root, query, criteriaBuilder) -> criteriaBuilder.isMember(request.userId(), root.get("followings"));
        Pageable pageable = PageUtils.of(request.limit(), request.offset());
        Page<User> users = userJpaRepository.findAll(specification, pageable);
        return users.map(user -> mapper.toPayLoad(user, self)).getContent();

    }
}