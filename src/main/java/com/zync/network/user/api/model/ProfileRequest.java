package com.zync.network.user.api.model;

import java.util.Set;

public record ProfileRequest(
        String bio,
        Set<String> links,
        String firstName,
        String lastName,
        String middleName
) {
}
