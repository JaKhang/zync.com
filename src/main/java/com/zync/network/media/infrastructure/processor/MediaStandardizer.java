package com.zync.network.media.infrastructure.processor;

import com.zync.network.core.domain.ZID;import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface MediaStandardizer {
    Path standardize(ZID id, Path path);
}
