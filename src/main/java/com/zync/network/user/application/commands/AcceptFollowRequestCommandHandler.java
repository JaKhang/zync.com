package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class AcceptFollowRequestCommandHandler implements NotificationHandler<AcceptFollowRequestCommand> {
    UserRepository userRepository;
    @Override
    public void handle(AcceptFollowRequestCommand notification) {
        User requester = userRepository.findById(notification.requesterId()).orElseThrow(() -> new ResourceNotFoundException("User", "Id", notification.requesterId()));
        User target = userRepository.findById(notification.targetId()).orElseThrow(() -> new ResourceNotFoundException("User", "Id", notification.requesterId()));
        target.acceptFollowRequest(requester);
        userRepository.save(requester);
        userRepository.save(target);
    }
}
