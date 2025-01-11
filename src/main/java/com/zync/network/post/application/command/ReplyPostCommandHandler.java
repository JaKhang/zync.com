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
public class ReplyPostCommandHandler implements RequestHandler<ReplyCommand, ZID> {
    PostRepository repository;

    @Override
    public ZID handle(ReplyCommand request) {
        Post post = repository.findById(request.postId()).orElseThrow(() -> new ResourceNotFoundException("Posts", "Id", request.postId()));
        Set<ZID> mentions = TextUtils.extractMentionIds(request.content());
        Post repost = post.reply(request.selfId(), request.content(), mentions, request.mediaIds(), request.visibility());
        return repository.save(repost);
    }
}
