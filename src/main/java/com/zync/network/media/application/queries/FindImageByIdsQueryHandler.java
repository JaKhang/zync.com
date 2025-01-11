package com.zync.network.media.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.payload.ImagePayload;
import com.zync.network.media.infrastructure.persistence.ImageJPARepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FindImageByIdsQueryHandler implements RequestHandler<FindImageByIdsQuery, List<ImagePayload>> {

    ImageJPARepository imageJPARepository;

    @Override
    public List<ImagePayload> handle(FindImageByIdsQuery request) {
        return List.of();
    }
}
