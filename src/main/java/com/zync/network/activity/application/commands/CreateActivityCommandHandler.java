package com.zync.network.activity.application.commands;

import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.domain.ActivityRepository;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreateActivityCommandHandler implements RequestHandler<CreateActivityCommand, ZID> {
    ActivityRepository repository;

    @Override
    public ZID handle(CreateActivityCommand request) {
        Activity activity = new Activity(
                request.type(),
                request.actorId(),
                request.targetId(),
                request.postId(),
                request.at()
        );
        return repository.save(activity);
    }
}
