package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MakeUserPublicCommandHandler implements NotificationHandler<MakeUserPublicCommand> {
    UserRepository userRepository;

    @Override
    public void handle(MakeUserPublicCommand notification) {
        User target = userRepository.findById(notification.id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.id().toLowerCase()));
        target.makePublic();
        userRepository.save(target);
    }
}
