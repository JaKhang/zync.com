package com.zync.network.media.infrastructure.utils;

import com.github.kokorin.jaffree.ffprobe.FFprobe;
import com.github.kokorin.jaffree.ffprobe.FFprobeResult;
import com.github.kokorin.jaffree.ffprobe.Stream;
import com.zync.network.media.domain.Dimension;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class FFmpegUtils {
    public  static Dimension getImageDimension(Path path){
        // Convert frame data to BufferedImage
        try {
            var image = ImageIO.read(new FileInputStream(path.toFile()));

            int width = image.getWidth();
            int height = image.getHeight();
            return new Dimension(width,height);
        } catch (IOException e) {
            return new Dimension(0,0);
        }


    }

    public  static Dimension geVideoDimension(Path path){
        FFprobe ffprobe = FFprobe.atPath();

        // Analyze the media file
        FFprobeResult result = ffprobe.setShowStreams(true)
                .setInput(path)
                .execute();

        Stream stream = result.getStreams().getFirst();
        return new Dimension(stream.getWidth(), stream.getHeight());


    }


}
