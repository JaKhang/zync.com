package com.zync.network.activity.application.payload;

import com.zync.network.activity.domain.Activity;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.factories.TimePayloadFactory;
import com.zync.network.post.application.clients.PostClient;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.user.application.clients.UserClient;
import com.zync.network.user.application.models.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ActivityMapper {
    private final UserClient userClient;
    private final TimePayloadFactory timePayloadFactory;
    private final PostClient postClient;

    public ActivityPayload map(Activity activity, UserPayload userPayload, PostPayLoad post) {
        return ActivityPayload.builder()
                .time(timePayloadFactory.create(activity.getCreatedAt()))
                .type(activity.getType())
                .post(post)
                .seen(activity.isSeen())
                .id(activity.getId())
                .actor(userPayload)
                .build();
    }

    public List<ActivityPayload> map(List<Activity> activities, ZID self) {
        Set<ZID> userIds = activities.stream().map(Activity::getActorId).collect(Collectors.toSet());
        Set<ZID> postIds = activities.stream().map(Activity::getPostId).collect(Collectors.toSet());
        Set<UserPayload> users = userClient.findByIds(userIds, self);
        Set<PostPayLoad> posts = postClient.findByIds(postIds, self);
        return activities.stream().map(activity -> map(activity, findUser(users, activity.getActorId()), findPost(posts, activity.getPostId()))).toList();
    }

    private UserPayload findUser(Set<UserPayload> userPayloads, ZID id) {
        return userPayloads.stream().filter(userPayload -> userPayload.id().equals(id)).findFirst().orElse(null);
    }

    private PostPayLoad findPost(Set<PostPayLoad> posts, ZID id) {
        if (id == null)
            return null;
        return posts.stream().filter(p -> p.id().equals(id)).findFirst().orElse(null);
    }
}
