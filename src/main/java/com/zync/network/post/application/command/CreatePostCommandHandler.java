package com.zync.network.post.application.command;

import com.zync.network.post.domain.PostType;
import com.zync.network.core.domain.ZID;
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
public class CreatePostCommandHandler implements RequestHandler<CreatePostCommand, ZID> {
    PostRepository postRepository;
    @Override
    public ZID handle(CreatePostCommand command) {
            Set<ZID> mentionTos = TextUtils.extractMentionIds(command.content());
            Post post = new Post(
                    command.mediaIds(),
                    command.content(),
                    mentionTos,
                    PostType.POST,
                    command.self(),
                    null,
                    ZID.fast(),
                    command.visibility()

            );
        
        return postRepository.save(post);
    }
}
