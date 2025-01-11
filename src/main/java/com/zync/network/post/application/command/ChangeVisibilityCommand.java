package com.zync.network.post.application.command;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;
import com.zync.network.post.domain.Visibility;

public record ChangeVisibilityCommand(ZID postId, ZID selfId, Visibility visibility) implements MediatorNotification {

}
