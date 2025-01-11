package com.zync.network.activity.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zync.network.core.domain.TimePayload;
import com.zync.network.core.domain.ZID;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.activity.domain.ActivityType;
import com.zync.network.user.application.models.UserPayload;
import lombok.Builder;

@Builder
public record ActivityPayload(
        ZID id,
        ActivityType type,
        UserPayload actor,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        PostPayLoad post,
        TimePayload time,
        boolean seen
) {
}
