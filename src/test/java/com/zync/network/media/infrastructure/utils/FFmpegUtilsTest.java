package com.zync.network.media.infrastructure.utils;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FFmpegUtilsTest {
    @Test
    void test(){
        System.out.println(FFmpegUtils.geVideoDimension(Path.of("C:\\Users\\PC\\Downloads\\file.mp4")));
    }
}