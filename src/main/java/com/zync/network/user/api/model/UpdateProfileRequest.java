package com.zync.network.user.api.model;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UpdateProfileRequest(
        String bio,
        List<String> links,
        @NotBlank
        String name
) {
}
