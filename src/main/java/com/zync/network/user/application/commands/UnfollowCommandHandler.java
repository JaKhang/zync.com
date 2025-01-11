package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.Relationship;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UnfollowCommandHandler implements RequestHandler<UnfollowCommand, Relationship> {
    UserRepository userRepository;

    @Override
    public Relationship handle(UnfollowCommand notification) {
        User target = userRepository.findById(notification.targetId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.targetId().toLowerCase()));
        User requester = userRepository.findById(notification.requesterId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.requesterId().toLowerCase()));
        requester.unfollow(target);
        userRepository.save(requester);
        userRepository.save(target);
        return requester.relationship(target);
    }
}
