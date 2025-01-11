package com.zync.network.user.application.queries;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.infrastructure.persistence.UserJpaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CheckFollowingQueryHandler implements RequestHandler<CheckFollowingQuery, Boolean> {
    UserJpaRepository userJpaRepository;

    @Override
    public Boolean handle(CheckFollowingQuery request) {
        User user = userJpaRepository.findById(request.target()).orElseThrow(() ->new  ResourceNotFoundException("Users", "Id", request.target()));
        return user.getFollowings().contains(request.self());
    }
}
