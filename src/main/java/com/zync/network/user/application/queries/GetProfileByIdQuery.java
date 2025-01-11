package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.user.application.models.ProfilePayload;

public record GetProfileByIdQuery(ZID selfId, ZID userId) implements MediatorRequest<ProfilePayload> {

}
