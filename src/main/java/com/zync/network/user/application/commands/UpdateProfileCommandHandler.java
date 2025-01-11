package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.user.domain.user.Name;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UpdateProfileCommandHandler implements NotificationHandler<UpdateProfileCommand> {
    private final UserRepository userRepository;


    @Override
    public void handle(UpdateProfileCommand notification) {
        Name name = new Name(notification.name(), null, null);
        User target = userRepository.findById(notification.userId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", notification.userId().toLowerCase()));
        target.update(name, notification.bio(), notification.links());
        userRepository.save(target);
    }
}
