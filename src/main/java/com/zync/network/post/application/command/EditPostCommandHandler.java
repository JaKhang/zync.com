package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.PermissionDeniedException;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostRepository;
import com.zync.network.post.infrastructure.utils.TextUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.Set;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EditPostCommandHandler implements NotificationHandler<EditPostCommand> {
    PostRepository postRepository;
    @Override
    public void handle(EditPostCommand notification) {
        Post post = postRepository.findById(notification.postId()).orElseThrow(() -> new ResourceNotFoundException("Posts", "Id", notification.postId()));
        if (!Objects.equals(post.getAuthorId(), notification.self())) throw new PermissionDeniedException();
        Set<ZID> mentions = TextUtils.extractMentionIds(notification.content());
        post.update(notification.content(), mentions, notification.mediaId());
        postRepository.save(post);
    }
}
