package com.zync.network.user.application.models;

import com.zync.network.media.application.clients.MediaClient;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final MediaClient mediaClient;
    public UserPayload toPayLoad(User user, User self){
        ImagePayload avatar = mediaClient.findImageById(user.getAvatarId());
        String avatS = avatar == null ? mediaClient.getDefaultAvatar() : avatar.url();
        return UserPayload.builder()
                .name(user.getFormatedName())
                .username(user.getUsername())
                .avatar(avatS)
                .id(user.getId())
                .isPrivate(user.isPrivate())
                .relationship(self.relationship(user).toString())
                .build();
    }
}
