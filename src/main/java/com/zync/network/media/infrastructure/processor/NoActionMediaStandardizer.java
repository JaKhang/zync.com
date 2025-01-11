package com.zync.network.media.infrastructure.processor;

import com.zync.network.core.domain.ZID;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class NoActionMediaStandardizer implements MediaStandardizer{
    @Override
    public Path standardize(ZID id, Path path) {
        return path;
    }
}
