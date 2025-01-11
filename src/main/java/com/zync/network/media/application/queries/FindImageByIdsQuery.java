package com.zync.network.media.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.media.application.payload.ImagePayload;

import java.util.List;

public record FindImageByIdsQuery(List<ZID> ids) implements MediatorRequest<List<ImagePayload>> {
}
