package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.NotificationHandler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UpdateImageCommandHandler implements NotificationHandler<UpdateImageCommand> {
    ImageRepository imageRepository;

    @Override
    public void handle(UpdateImageCommand request) {
        Image image = imageRepository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Image", "Id", request.id().toLowerCase()));
        image.update(request.reference(), request.size(), request.uploaded(), request.dimension(), request.variants(), request.mediaType().toString());
        imageRepository.save(image);
    }
}
