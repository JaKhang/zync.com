package com.zync.network.media.infrastructure.processor;

import com.github.kokorin.jaffree.ffmpeg.FFmpeg;
import com.github.kokorin.jaffree.ffmpeg.UrlInput;
import com.github.kokorin.jaffree.ffmpeg.UrlOutput;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.infrastructure.utils.FileNameUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Slf4j
@Component
public class FFmpegImageProcessor implements ImageProcessor {

    @Value("${application.media.storage.tmp}")
    private String tempDir;

    @Override
    public Path resize(Dimension dimension, Path src, String format) {
        Path dest = Path.of(tempDir, FileNameUtils.generateRandomBaseName("filename." + format));
        var res = FFmpeg.atPath()
                .addInput(UrlInput.fromPath(src))
                .addOutput(
                        UrlOutput.toPath(dest).
                                addArguments("-vf", "scale=" + dimension.width() + ":" + dimension.height())
                ).execute();
        return dest;
    }

    @Override
    @Async
    public void test() {
        log.info(Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
