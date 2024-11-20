package com.zync.network.media.presentation.models;

import java.util.List;

public record ImagePayload(
    List<VariantPayload> variant
) {
    public record VariantPayload(
            String url,
            int width,
            int height
    ){

    }
}
