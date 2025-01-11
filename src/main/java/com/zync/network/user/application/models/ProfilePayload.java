package com.zync.network.user.application.models;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.Gender;
import com.zync.network.user.domain.user.Relationship;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
public record ProfilePayload(
        ZID id,
        String name,
        String bio,
        LocalDate dateOfBirth,
        String avatar,
        String username,
        Set<String> links,
        Gender gender,
        int numberOfFollowers,
        int numberOfFollowings,
        boolean isPrivate,
        Relationship relationship
) {


}
