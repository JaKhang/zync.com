package com.zync.network.post.application.command;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UnlikePostCommandHandler implements NotificationHandler<UnlikePostCommand> {
    PostRepository postRepository;
    @Override
    public void handle(UnlikePostCommand notification) {
        Post post = postRepository.findById(notification.postId()).orElseThrow(() -> new ResourceNotFoundException("Posts", "Id", notification.postId()));
        post.unlike(notification.selfId());
        postRepository.save(post);
    }
}
