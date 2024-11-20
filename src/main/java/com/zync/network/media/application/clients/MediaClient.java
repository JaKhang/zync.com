package com.zync.network.media.application.clients;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.presentation.models.ImagePayload;
import com.zync.network.media.presentation.models.MediaClientPayload;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface MediaClient {

    Set<ZID> uploadPostMedia(List<MultipartFile> mediaFiles, ZID userId);

    Set<MediaClientPayload> findMedia(Set<ZID> mediaId);

    SimpleImagePayload findImageById(ZID id);

    Set<ImagePayload> findImageByIds(Set<ZID> ids);

    ZID uploadImage(MultipartFile file, ZID userId, DimensionRequest dimensions, List<Dimension> variant);

}
