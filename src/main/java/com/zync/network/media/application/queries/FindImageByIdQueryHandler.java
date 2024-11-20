package com.zync.network.media.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.payload.ImageResponse;
import com.zync.network.media.infrastructure.persistence.ImageJPARepository;
import lombok.RequiredArgsConstructor;

@Handler
@RequiredArgsConstructor
public class FindImageByIdQueryHandler implements RequestHandler<FindImageByIdQuery, ImageResponse> {

    private final ImageJPARepository imageJPARepository;

    @Override
    public ImageResponse handle(FindImageByIdQuery request) {
         return imageJPARepository.findById(request.imageId(), ImageResponse.class);
    }
}
