package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;
@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindFollowingIdsQueryHandler implements RequestHandler<FindFollowingIdsQuery, Set<ZID>> {
    UserRepository userRepository;

    @Override
    public Set<ZID> handle(FindFollowingIdsQuery request) {
        User target = userRepository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", request.id().toLowerCase()));
        return target.getFollowings();
    }
}
