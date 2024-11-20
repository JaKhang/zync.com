package com.zync.network.media.infrastructure.storage;

import lombok.Builder;

@Builder
public record FilePermission(
        boolean readable,
        boolean writeable,
        boolean executable
) {
}
