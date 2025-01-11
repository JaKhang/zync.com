package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostRepository;
import com.zync.network.post.infrastructure.utils.TextUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RepostCommandHandler implements RequestHandler<RepostCommand, ZID> {
    PostRepository postRepository;

    @Override
    public ZID handle(RepostCommand request) {
        Post post = postRepository.findById(request.postId()).orElseThrow(() -> new ResourceNotFoundException("Posts", "Id", request.postId()));
        Set<ZID> mentionids = TextUtils.extractMentionIds(request.content());
        Post repost = post.repost(request.self(),request.content(), request.mediaIds(), mentionids ,request.visibility());
        return postRepository.save(repost);
    }
}
