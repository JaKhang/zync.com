package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CreateImageCommandHandler implements RequestHandler<CreateImageCommand, ZID> {
    ImageRepository imageRepository;

    @Override
    public ZID handle(CreateImageCommand request) {
        Image image = new Image(request.id(), request.uploadBy(), request.size(), request.reference(), request.uploaded(), request.mediaType().toString(), request.dimension(), request.variants());
        return imageRepository.save(image);
    }
}
