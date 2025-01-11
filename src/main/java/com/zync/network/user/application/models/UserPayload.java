package com.zync.network.user.application.models;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.Relationship;
import lombok.Builder;

@Builder
public record UserPayload(
        ZID id,
        String username,
        String avatar,
        String name,
        String relationship,
        boolean isPrivate,
        boolean verified
) {
}
