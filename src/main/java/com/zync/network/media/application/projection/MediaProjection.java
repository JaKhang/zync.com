package com.zync.network.media.application.projection;

import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

public interface MediaProjection {
    @Value("#{@URLFactory.generate(target.reference)}")
    String getUrl();

    String mediaType();

    default Type getType(){
        return Type.valueOf(MediaType.parseMediaType(mediaType()).getType().toUpperCase());
    }

    enum Type{
        IMAGE,
        VIDEO,
        AUDIO
    }
}
