package com.zync.network.user.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Notification;
import com.zync.network.core.mediator.Request;
import com.zync.network.user.application.models.ProfileProjection;

public class GetProfileByIdQuery implements Request<ProfileProjection> {
    public GetProfileByIdQuery(ZID id) {
    }
}
