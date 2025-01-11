package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorNotification;

import java.util.List;

public record UpdateProfileCommand(
        ZID userId,
        List<String> links,
        String bio,
        String name
) implements MediatorNotification {
}
