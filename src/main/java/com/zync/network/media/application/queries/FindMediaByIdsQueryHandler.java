package com.zync.network.media.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.media.application.payload.mapper.MediaMapper;
import com.zync.network.media.domain.Media;
import com.zync.network.media.infrastructure.persistence.MediaJpaRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
@Handler
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FindMediaByIdsQueryHandler implements RequestHandler<FindMediaByIdsQuery, List<MediaPayLoad>> {
    MediaJpaRepository mediaRepository;
    MediaMapper mapper;
    @Override
    public List<MediaPayLoad> handle(FindMediaByIdsQuery request) {
        List<Media> media = mediaRepository.findAllById(request.ids());
        return mapper.map(media);
    }
}
