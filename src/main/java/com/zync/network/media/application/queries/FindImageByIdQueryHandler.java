package com.zync.network.media.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.media.infrastructure.persistence.ImageJPARepository;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class FindImageByIdQueryHandler implements RequestHandler<FindImageByIdQuery, ImagePayload> {

    private final ImageJPARepository imageJPARepository;

    @Override
    public ImagePayload handle(FindImageByIdQuery request) {
         return imageJPARepository.findById(request.imageId(), ImagePayload.class);
    }
}
