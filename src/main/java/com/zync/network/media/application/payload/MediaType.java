package com.zync.network.media.application.payload;

public enum MediaType {
    VIDEO,
    AUDIO,
    IMAGE;
    public static MediaType fromMime(String mime){
        if (mime.startsWith("image")) return IMAGE;
        if (mime.startsWith("video")) return VIDEO;
        if (mime.startsWith("AUDIO")) return AUDIO;
        throw new IllegalArgumentException();
    }
}
