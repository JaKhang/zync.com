package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RemoveFollowRequestHandler implements NotificationHandler<RemoveFollowRequestCommand> {

    UserRepository userRepository;

    @Override
    public void handle(RemoveFollowRequestCommand notification) {
        User target = userRepository.findById(notification.targetId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.targetId().toLowerCase()));
        User self = userRepository.findById(notification.selfId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.selfId().toLowerCase()));
        self.removeFollowRequest(target);
        userRepository.save(target);
    }
}
