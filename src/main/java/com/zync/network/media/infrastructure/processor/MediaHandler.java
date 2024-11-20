package com.zync.network.media.infrastructure.processor;

import com.zync.network.core.domain.ZID;import org.springframework.web.multipart.MultipartFile;

public interface MediaHandler {
    void handle(ZID id, MultipartFile file);
}
