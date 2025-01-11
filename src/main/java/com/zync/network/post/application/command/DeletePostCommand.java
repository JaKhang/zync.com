package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

public record DeletePostCommand(ZID postId, ZID selfId) implements MediatorNotification {
}
