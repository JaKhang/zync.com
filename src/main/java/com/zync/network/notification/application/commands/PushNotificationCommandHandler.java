package com.zync.network.notification.application.commands;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PushNotificationCommandHandler implements NotificationHandler<PushNotificationCommand> {
    SimpMessagingTemplate messagingTemplate;
    @Override
    public void handle(PushNotificationCommand command) {
        if (command.recipientId().equals(command.actorId())) return;
        System.out.println(command);
        messagingTemplate.convertAndSendToUser(
                command.recipientId().toString(),
                "/notification",
                command.actorId().toString()
        );
    }
}
