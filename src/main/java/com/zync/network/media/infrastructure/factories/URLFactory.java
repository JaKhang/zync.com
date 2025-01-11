package com.zync.network.media.infrastructure.factories;

import com.zync.network.media.domain.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLFactory {
    @Value("${application.media.host}")
    private String mediaHostName;

    public String generate(Media reference){
        return reference == null ? "" : mediaHostName.concat(reference.getReference());
    }

    public String generateAvatar(Media reference){
        return reference == null ? mediaHostName.concat("/images/01JGVN7CFWJHJYDYCTA3FFNDGC.jpg") : mediaHostName.concat(reference.getReference());
    }

}
