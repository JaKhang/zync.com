package com.zync.network.media.application.clients;

public record SimpleImagePayload(String url,
                                 int width,
                                 int height) {
}
