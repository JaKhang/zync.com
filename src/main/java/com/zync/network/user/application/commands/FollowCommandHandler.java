package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.transaction.annotation.Transactional;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FollowCommandHandler implements NotificationHandler<FollowCommand> {
    UserRepository userRepository;
    @Override
    @Transactional
    public void handle(FollowCommand notification) {
        User target = userRepository.findById(notification.targetId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.targetId().toLowerCase()));
        User requester = userRepository.findById(notification.requesterId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.requesterId().toLowerCase()));
        requester.requestToFollow(target);
        userRepository.save(target);
        userRepository.save(requester);
    }
}
