package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.clients.MediaClient;
import com.zync.network.media.application.clients.DimensionRequest;
import com.zync.network.user.domain.profile.Profile;
import com.zync.network.user.domain.profile.ProfileRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Handler
public class UploadAvatarCommandHandler implements RequestHandler<UploadAvatarCommand, ZID> {

    MediaClient mediaClient;
    ProfileRepository profileRepository;
    int avatarHeight;
    int avatarWith;

    public UploadAvatarCommandHandler(MediaClient mediaClient,
                                      ProfileRepository profileRepository,
                                      @Value("${application.media.image.avatar.height}") int avatarHeight,
                                      @Value("${application.media.image.avatar.width}") int avatarWith
    ) {
        this.mediaClient = mediaClient;
        this.profileRepository = profileRepository;
        this.avatarHeight = avatarHeight;
        this.avatarWith = avatarWith;
    }

    @Override
    public ZID handle(UploadAvatarCommand request) {
        Profile profile = profileRepository.findById(request.userId()).orElseThrow(()-> new ResourceNotFoundException("USER", "Id", request.userId().toLowerCase()));
        ZID avatar = mediaClient.uploadImage(request.file(), request.userId(), new DimensionRequest(avatarWith, avatarHeight), List.of());
        profile.setAvatarId(avatar);
        return profileRepository.save(profile);
    }
}
