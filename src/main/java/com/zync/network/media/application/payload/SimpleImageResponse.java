package com.zync.network.media.application.payload;

import org.springframework.beans.factory.annotation.Value;

public interface SimpleImageResponse {
    String getUrl();
    @Value("#{target.dimension.width}")
    int getWidth();
    @Value("#{target.dimension.height}")
    int getHeight();
}
