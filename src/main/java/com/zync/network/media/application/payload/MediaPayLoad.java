package com.zync.network.media.application.payload;

import com.zync.network.core.domain.ZID;
import lombok.Builder;

@Builder
public record MediaPayLoad(
        String url,
        String type,
        ZID id,
        int width,
        int height
) {
}
