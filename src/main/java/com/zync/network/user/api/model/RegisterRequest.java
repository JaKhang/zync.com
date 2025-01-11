package com.zync.network.user.api.model;

import com.zync.network.user.domain.user.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record RegisterRequest(
        @Pattern(regexp = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$")
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String middleName,
        LocalDate dateOfBirth,
        @NotNull(message = "Gender must not be null")
        Gender gender
) {
}
