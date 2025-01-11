package com.zync.network.activity.application.commands;

import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.domain.ActivityRepository;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeleteActivityByCommandHandler implements NotificationHandler<DeleteActivityByCommand> {
    ActivityRepository repository;

    @Override
    public void handle(DeleteActivityByCommand request) {
        Activity activity = repository.findByTypeAndActorIdAndPostId(request.type(), request.actorId(), request.postId());
        repository.delete(activity.getId());
    }
}
