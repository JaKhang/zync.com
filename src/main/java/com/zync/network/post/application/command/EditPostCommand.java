package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.post.domain.Visibility;

import java.util.List;

public record EditPostCommand(ZID postId, ZID self, String content, List<ZID> mediaId,
                              Visibility visibility) implements MediatorNotification {
}
