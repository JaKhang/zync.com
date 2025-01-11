package com.zync.network.post.application.command;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostRepository;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ChangeVisibilityCommandHandler implements NotificationHandler<ChangeVisibilityCommand> {
    PostRepository postRepository;
    @Override
    public void handle(ChangeVisibilityCommand notification) {
        Post post = postRepository.findById(notification.postId()).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", notification.postId()));
        post.setVisibility(post.getVisibility());
        postRepository.save(post);
    }
}
