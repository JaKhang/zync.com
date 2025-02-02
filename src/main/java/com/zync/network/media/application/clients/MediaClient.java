package com.zync.network.media.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.media.domain.Dimension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MediaClient {

    List<ZID> uploadPostMedia(List<MultipartFile> mediaFiles, ZID userId);

    List<MediaPayLoad> findMedia(List<ZID> mediaId);

    ImagePayload findImageById(ZID id);

    List<ImagePayload> findImageByIds(List<ZID> ids);

    ZID uploadImage(MultipartFile file, ZID userId, DimensionRequest dimensions, List<Dimension> variant);

    ImagePayload generateImageUrl(ZID imageId);

    String getDefaultAvatar();
}
