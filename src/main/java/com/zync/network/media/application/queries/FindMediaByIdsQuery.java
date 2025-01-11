package com.zync.network.media.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.media.application.payload.MediaPayLoad;

import java.util.List;

public record FindMediaByIdsQuery(List<ZID> ids) implements MediatorRequest<List<MediaPayLoad>> {
}
