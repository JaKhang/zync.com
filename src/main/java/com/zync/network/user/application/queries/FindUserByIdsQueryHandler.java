package com.zync.network.user.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.application.models.UserMapper;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.infrastructure.persistence.UserJpaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FindUserByIdsQueryHandler implements RequestHandler<FindUserByIdsQuery, Set<UserPayload>> {
    UserJpaRepository userJpaRepository;
    UserMapper userMapper;
    @Override
    public Set<UserPayload> handle(FindUserByIdsQuery request) {
        List<User> users = userJpaRepository.findAllById(request.ids());
        User self = userJpaRepository.findById(request.selfId()).orElseThrow();
        return users.stream().map(user -> userMapper.toPayLoad(user, self)).collect(Collectors.toSet());
    }
}
