package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.post.domain.Visibility;

import java.util.List;

public record CreatePostCommand(ZID self, String content, List<ZID> mediaIds, Visibility visibility) implements MediatorRequest<ZID> {
}
