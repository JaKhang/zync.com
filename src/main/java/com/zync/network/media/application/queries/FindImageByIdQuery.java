package com.zync.network.media.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Request;
import com.zync.network.media.application.payload.ImageResponse;

public record FindImageByIdQuery(ZID imageId) implements Request<ImageResponse> {
}
