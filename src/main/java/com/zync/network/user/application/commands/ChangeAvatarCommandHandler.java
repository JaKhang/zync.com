package com.zync.network.user.application.commands;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.user.domain.profile.Profile;
import com.zync.network.user.domain.profile.ProfileRepository;

@Handler
public class ChangeAvatarCommandHandler implements NotificationHandler<ChangeAvatarCommand> {
    ProfileRepository profileRepository;

    @Override
    public void handle(ChangeAvatarCommand notification) {
        Profile profile = profileRepository.findById(notification.userId()).orElseThrow(() -> new ResourceNotFoundException("Profile", "id", notification.userId().toLowerCase()));
        profile.changeAvatar(notification.avatarId());
    }
}
