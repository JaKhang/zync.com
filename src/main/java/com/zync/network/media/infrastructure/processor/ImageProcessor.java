package com.zync.network.media.infrastructure.processor;

import com.zync.network.media.domain.Dimension;

import java.nio.file.Path;

public interface ImageProcessor {
    Path resize(Dimension dimension, Path src, String format);

    void test();



}


