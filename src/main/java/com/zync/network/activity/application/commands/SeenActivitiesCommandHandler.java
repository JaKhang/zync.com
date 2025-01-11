package com.zync.network.activity.application.commands;

import com.zync.network.activity.domain.Activity;
import com.zync.network.activity.domain.ActivityRepository;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeenActivitiesCommandHandler implements NotificationHandler<SeenActivitiesCommand> {
    ActivityRepository repository;

    @Override
    public void handle(SeenActivitiesCommand notification) {
        var ac = repository.findAllById(notification.ids());
        ac.forEach(Activity::seen);
        ac.forEach(repository::save);
    }
}
