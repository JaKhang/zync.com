package com.zync.network.media.application.payload;

import com.zync.network.core.domain.ZID;
import org.springframework.beans.factory.annotation.Value;

    public interface ImagePayload {

        @Value("#{target.dimenssion.width}")
        int getWidth();
        @Value("#{target.dimenssion.height}")
        int getHeight();
        @Value("#{@URLFactory.generate(target)}")
        String url();
    }
