package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.clients.MediaClient;
import com.zync.network.media.application.clients.DimensionRequest;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Handler
public class UploadAvatarCommandHandler implements RequestHandler<UploadAvatarCommand, ZID> {

    MediaClient mediaClient;
    UserRepository userRepository;
    int avatarHeight;
    int avatarWith;

    public UploadAvatarCommandHandler(MediaClient mediaClient,
                                      UserRepository userRepository,
                                      @Value("${application.media.image.avatar.height}") int avatarHeight,
                                      @Value("${application.media.image.avatar.width}") int avatarWith
    ) {
        this.mediaClient = mediaClient;
        this.userRepository = userRepository;
        this.avatarHeight = avatarHeight;
        this.avatarWith = avatarWith;
    }

    @Override
    @Transactional
    public ZID handle(UploadAvatarCommand request) {
        User user = userRepository.findById(request.userId()).orElseThrow(()-> new ResourceNotFoundException("USER", "Id", request.userId().toLowerCase()));
        ZID avatar = mediaClient.uploadImage(request.file(), request.userId(), new DimensionRequest(avatarWith, avatarHeight), List.of());
        user.setAvatarId(avatar);
        return userRepository.save(user);
    }
}
