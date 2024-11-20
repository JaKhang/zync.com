package com.zync.network.media.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.modules.media")
public record MediaProperties(

) {

    public record Image(

    ){

    }

    public static class Dimension{

    }
}
