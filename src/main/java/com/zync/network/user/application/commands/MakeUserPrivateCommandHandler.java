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
public class MakeUserPrivateCommandHandler implements NotificationHandler<MakeUserPrivateCommand> {
    UserRepository userRepository;

    @Override
    public void handle(MakeUserPrivateCommand notification) {
        User target = userRepository.findById(notification.userId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.userId().toLowerCase()));
        target.makePrivate();
        userRepository.save(target);
    }
}
