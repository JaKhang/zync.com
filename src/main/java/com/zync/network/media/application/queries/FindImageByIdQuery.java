package com.zync.network.media.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.media.application.payload.ImagePayload;

public record FindImageByIdQuery(ZID imageId) implements MediatorRequest<ImagePayload> {
}
