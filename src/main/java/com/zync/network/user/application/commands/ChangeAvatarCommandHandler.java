package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;

@Handler
public class ChangeAvatarCommandHandler implements NotificationHandler<ChangeAvatarCommand> {
    UserRepository userRepository;

    @Override
    public void handle(ChangeAvatarCommand notification) {
        User user = userRepository.findById(notification.userId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", notification.userId().toLowerCase()));
        user.changeAvatar(notification.avatarId());
    }
}
