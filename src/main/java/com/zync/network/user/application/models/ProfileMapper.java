package com.zync.network.user.application.models;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.application.clients.MediaClient;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.user.domain.user.Relationship;
import com.zync.network.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ProfileMapper {
    private final MediaClient mediaClient;
    public ProfilePayload toPayLoad(User user, User self){
        ImagePayload avatar = mediaClient.findImageById(user.getAvatarId());
        String avatS = avatar == null ? mediaClient.getDefaultAvatar() : avatar.url();
       return ProfilePayload.builder()
               .name(user.getFormatedName())
               .username(user.getUsername())
               .links(user.getLinks())
               .gender(user.getGender())
               .bio(user.getBio())
               .avatar(avatS)
               .numberOfFollowings(Hibernate.size(user.getFollowings()))
               .numberOfFollowers(Hibernate.size(user.getFollowers()))
               .id(user.getId())
               .isPrivate(user.isPrivate())
               .dateOfBirth(user.getDateOfBirth())
               .relationship(self.relationship(user))
               .build();
    }

    private Relationship getFollowStatus(User user, ZID selfId){
        if (user.isFollowRequesting(selfId)) return Relationship.PENDING;
        if (user.getFollowers().contains(selfId)) return Relationship.FOLLOWED;
        return Relationship.NONE;
    }

    public ProfilePayload toPrivateProfile(User user, User self){
        ImagePayload avatar = mediaClient.findImageById(user.getAvatarId());
        String avatS = avatar == null ? mediaClient.getDefaultAvatar() : avatar.url();
        return ProfilePayload.builder()
                .name(user.getFormatedName())
                .username(user.getUsername())
                .gender(user.getGender())
                .links(Set.of())
                .isPrivate(true)
                .bio("")
                .avatar(avatS)
                .numberOfFollowings(0)
                .numberOfFollowers(0)
                .id(user.getId())
                .relationship(self.relationship(user))
                .dateOfBirth(null)
                .build();
    }
}
