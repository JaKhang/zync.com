package com.zync.network.notification.application.events;


import com.zync.network.account.domain.events.ResetPasswordCodeCreatedEvent;
import com.zync.network.account.domain.events.VerifyCodeCreatedEvent;
import com.zync.network.activity.domain.ActivityCreatedEvent;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.notification.application.commands.PushNotificationCommand;
import com.zync.network.notification.application.commands.SendResetPasswordEmailCommand;
import com.zync.network.notification.application.commands.SendVerifyEmailCommand;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;

@Slf4j
@Handler
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventHandler {
    Mediator mediator;


    @ApplicationModuleListener
    public void on(VerifyCodeCreatedEvent event){
        mediator.send(new SendVerifyEmailCommand(event.email(), event.code(), 5));
    }

    @ApplicationModuleListener
    public void on(ResetPasswordCodeCreatedEvent event){
        mediator.send(new SendResetPasswordEmailCommand(event.email(), event.code(), 5));
    }

    @ApplicationModuleListener
    public void on(ActivityCreatedEvent event){
        mediator.send(new PushNotificationCommand(event.actorId(), event.recipientId(), event.postId(), event.type(), event.at()));
    }

}
