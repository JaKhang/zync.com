package com.zync.network.media.application.clients;

import com.zync.network.media.application.queries.FindImageByIdQuery;
import com.zync.network.media.application.queries.FindImageByIdsQuery;
import com.zync.network.media.application.queries.FindMediaByIdsQuery;
import com.zync.network.media.infrastructure.factories.URLFactory;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.media.domain.Dimension;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.media.application.commands.UploadImageCommand;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DefaultMediaClient implements MediaClient{

    Mediator mediator;
    URLFactory factory;


    @Override
    public List<ZID> uploadPostMedia(List<MultipartFile> mediaFiles, ZID userId) {
        return List.of();
    }

    @Override
    public List<MediaPayLoad> findMedia(List<ZID> mediaId) {
        return mediator.send(new FindMediaByIdsQuery(mediaId));
    }

    @Override
    public ImagePayload findImageById(ZID id) {
        return mediator.send(new FindImageByIdQuery(id));
    }

    @Override
    public List<ImagePayload> findImageByIds(List<ZID> ids) {
        // TODO (PC, 20/10/2024): To change the body of an implemented method
        return mediator.send(new FindImageByIdsQuery(ids));
    }


    @Override
    public ZID uploadImage(MultipartFile file, ZID userId, DimensionRequest dimensions, List<Dimension> variants) {
        return mediator.send(new UploadImageCommand(file, userId, new Dimension(dimensions.width(), dimensions.height()), variants));
    }

    @Override
    public ImagePayload generateImageUrl(ZID imageId) {
        return mediator.send(new FindImageByIdQuery(imageId));
    }

    @Override
    public String getDefaultAvatar() {
        return factory.generateAvatar(null);
    }


}
