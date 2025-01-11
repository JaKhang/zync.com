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
public class DeletePostCommandHandler implements NotificationHandler<DeletePostCommand> {

    PostRepository postRepository;

    @Override
    public void handle(DeletePostCommand notification) {
        Post post = postRepository.findById(notification.postId()).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", notification.postId()));
        post.setDeleted(true);
        postRepository.save(post);
    }
}
