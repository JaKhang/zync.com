package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Request;

import java.time.LocalDate;
import java.util.Locale;

public record CreateProfileCommand(
        String firstName,
        String lastName,
        String middleName,
        LocalDate dateOfBirth,
        Locale locale,
        String email,
        String password
) implements Request<ZID> {
}
