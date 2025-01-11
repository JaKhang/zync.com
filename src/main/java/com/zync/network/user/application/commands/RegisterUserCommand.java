package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.domain.user.Gender;

import java.time.LocalDate;
import java.util.Locale;

public record RegisterUserCommand(
        String username,
        String firstName,
        String lastName,
        String middleName,
        LocalDate dateOfBirth,
        Locale locale,
        String email,
        String password,
        Gender gender
) implements MediatorRequest<ZID> {
}
