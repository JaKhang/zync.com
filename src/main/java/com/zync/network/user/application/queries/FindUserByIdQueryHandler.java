package com.zync.network.user.application.queries;

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

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FindUserByIdQueryHandler implements RequestHandler<FindUserByIdQuery, UserPayload> {
    UserMapper mapper;
    UserJpaRepository userJpaRepository;
    @Override
    public UserPayload handle(FindUserByIdQuery request) {
        User user = userJpaRepository.findById(request.zid()).orElseThrow(() ->new ResourceNotFoundException("Users", "Id", request.zid()));
        User self = userJpaRepository.findById(request.self()).orElseThrow(() ->new ResourceNotFoundException("Users", "Id", request.self()));
        return mapper.toPayLoad(user, self);
    }
}
