package com.zync.network.user.api.model;

import java.time.LocalDate;

public record ProfileRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String middleName,
        LocalDate dateOfBirth
) {
}
