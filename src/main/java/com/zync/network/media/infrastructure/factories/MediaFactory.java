package com.zync.network.media.infrastructure.factories;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.Media;
import com.zync.network.media.domain.Video;
import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MediaFactory {
    public Media create(ZID userId, MultipartFile file){
        String name = file.getOriginalFilename();
        if(MediaTypeUtil.isImageFile(name))
            return createImage(userId, file);
        if (MediaTypeUtil.isAudioFile(name))
            return createAudio(userId, file);
        if (MediaTypeUtil.isVideoFile(name))
            return createVideo(userId, file);
        throw new IllegalArgumentException();
    }

    private Media createVideo(ZID userId, MultipartFile file) {
        return new Video(
                ZID.fast(),
                userId,
                file.getSize(),
                "",
                false,
                file.getContentType(),
                null,
                null
        );
    }

    private Media createAudio(ZID userId, MultipartFile file) {
        return null;
    }

    private Media createImage(ZID userId, MultipartFile file) {
        return new Image(
                ZID.fast(),
                userId,
                file.getSize(),
                "",
                false,
                file.getContentType(),
                null,
                null
        );
    }
}
