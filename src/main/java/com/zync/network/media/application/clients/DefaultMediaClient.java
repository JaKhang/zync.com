package com.zync.network.media.application.clients;

import com.zync.network.media.application.payload.ImageResponse;
import com.zync.network.media.application.queries.FindImageByIdQuery;
import com.zync.network.media.domain.Dimension;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.media.application.commands.UploadImageCommand;
import com.zync.network.media.presentation.models.ImagePayload;
import com.zync.network.media.presentation.models.MediaClientPayload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class DefaultMediaClient implements MediaClient{

    Mediator mediator;



    @Override
    public Set<ZID> uploadPostMedia(List<MultipartFile> mediaFiles, ZID userId) {
        // TODO (PC, 22/10/2024): To change the body of an implemented method
        return Set.of();
    }

    @Override
    public Set<MediaClientPayload> findMedia(Set<ZID> mediaId) {
        return Set.of();
    }

    @Override
    public SimpleImagePayload findImageById(ZID id) {
        ImageResponse imageResponse = mediator.send(new FindImageByIdQuery(id));
        return new SimpleImagePayload(imageResponse.getUrl(), imageResponse.getWidth(), imageResponse.getHeight());
    }

    @Override
    public Set<ImagePayload> findImageByIds(Set<ZID> ids) {
        // TODO (PC, 20/10/2024): To change the body of an implemented method
        return Set.of();
    }


    @Override
    public ZID uploadImage(MultipartFile file, ZID userId, DimensionRequest dimensions, List<Dimension> variants) {
        return mediator.send(new UploadImageCommand(file, userId, new Dimension(dimensions.width(), dimensions.height()), variants));
    }
}
