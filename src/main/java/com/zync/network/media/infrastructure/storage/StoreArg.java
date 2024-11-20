package com.zync.network.media.infrastructure.storage;

import lombok.Builder;

import java.io.InputStream;
@Builder
public record StoreArg(InputStream stream, long size, String path, String contentType, FilePermission permission) {

}
