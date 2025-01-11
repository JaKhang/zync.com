package com.zync.network.account.application.queries;

import com.zync.network.core.domain.ZID;import com.zync.network.account.application.model.DevicePayload;
import com.zync.network.core.mediator.MediatorRequest;

import java.util.List;

public record SignedDevicesQuery(ZID accountId) implements MediatorRequest<List<DevicePayload>> {

}
