package com.zync.network.post.application.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zync.network.core.domain.TimePayload;
import com.zync.network.core.domain.ZID;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.user.application.models.UserPayload;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostPayLoad(
        ZID id,
        UserPayload author,
        String type,
        String content,
        LocalDateTime createdAt,
        int likes,
        int replies,
        int reposts,
        List<MediaPayLoad> media,
        TimePayload time,
        boolean liked,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        PostPayLoad parent
) {
}
