package com.zync.network.account.domain.events;

import com.zync.network.core.domain.ZID;
public record AuthenticatedAnonymousDeviceEvent(ZID accountId){
}
