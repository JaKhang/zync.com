package com.zync.network.activity.application.events;


import com.zync.network.activity.application.commands.CreateActivityCommand;
import com.zync.network.activity.application.commands.DeleteActivityByCommand;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.activity.domain.ActivityType;
import com.zync.network.post.domain.events.PostRePostedEvent;
import com.zync.network.post.domain.events.LikedPostEvent;
import com.zync.network.post.domain.events.PostRepliedEvent;
import com.zync.network.post.domain.events.UnlikedPostEvent;
import com.zync.network.user.domain.events.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Log4j2
public class ActivityEventHandler {
    Mediator mediator;

    @ApplicationModuleListener
    public void on(LikedPostEvent event){
        mediator.send(new CreateActivityCommand(event.userId(), event.authorId(), event.postId(),event.at(), ActivityType.LIKE));
    }

    @ApplicationModuleListener
    public void on(UnfollowedEvent event){
        mediator.send(new DeleteActivityByCommand(event.requester(), event.target(), ActivityType.FOLLOW));
    }

    @ApplicationModuleListener
    public void on(PostRepliedEvent event){
        log.info("Handle Post replied event");
        mediator.send(new CreateActivityCommand(event.userId(), event.authorId(), event.postId(), event.at(),ActivityType.REPLY));
        on(event.mentionIds(), event.postId(), event.authorId(), event.at());
    }

    @ApplicationModuleListener
    public void on(PostRePostedEvent event){
        log.info("Handle repost created");
        if (event.userId().equals(event.postId()))
            return;
        on(event.mentionIds(), event.postId(), event.authorId(), event.at());

    }

    @ApplicationModuleListener
    public void on(PostCreatedEvent event){
        on(event.mentionIds(), event.postId(), event.authorId(), event.at());
    }

    @ApplicationModuleListener
    public void on(UserFollowedEvent event){
        mediator.send(new CreateActivityCommand(event.follower(), event.userId(), null, event.at(), ActivityType.FOLLOW));
    }

    @ApplicationModuleListener
    public void on(FollowRequestCreatedEvent event){
        mediator.send(new CreateActivityCommand(event.requesterId(), event.targetUserId(), null, event.at(), ActivityType.REQUEST_FOLLOW));
    }

    @ApplicationModuleListener
    public void on(FollowRequestAcceptedEvent event){
        mediator.send(new CreateActivityCommand(event.requesterId(), event.targetUserId(), null, event.at(), ActivityType.ACCEPT_FOLLOW));
    }

    @ApplicationModuleListener
    public void on(UnlikedPostEvent event){
        mediator.send(new DeleteActivityByCommand(event.userId(), event.postId(), ActivityType.LIKE));
    }


    private void on(Set<ZID> mentionIds, ZID postId, ZID authorId, LocalDateTime at){
        for (ZID mentionId : mentionIds) {
            if (Objects.equals(mentionId, authorId)) continue;
            mediator.send(new CreateActivityCommand(authorId, mentionId, postId, at,ActivityType.MENTION));
        }
    }




}
