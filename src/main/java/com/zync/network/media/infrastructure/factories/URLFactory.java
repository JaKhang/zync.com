package com.zync.network.media.infrastructure.factories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLFactory {
    @Value("${application.media.host}")
    private String mediaHostName;

    public String generate(String reference){
        return mediaHostName.concat(reference);
    }
}
