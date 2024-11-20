package com.zync.network.media.application.payload;

import com.zync.network.core.domain.ZID;
import org.springframework.beans.factory.annotation.Value;


import java.util.Set;
public interface ImageResponse {
    ZID getId();
    Set<VariantProjection> getVariants();
    @Value("#{@URLFactory.generate(target.reference)}")
    String getUrl();
    @Value("#{target.dimension.width}")
    int getWidth();
    @Value("#{target.dimension.height}")
    int getHeight();

    interface VariantProjection{
        @Value("#{@URLFactory.generate(target.reference)}")
        String getUrl();
        @Value("#{target.dimension.width}")
        int getWidth();
        @Value("#{target.dimension.height}")
        int getHeight();
    }
}
