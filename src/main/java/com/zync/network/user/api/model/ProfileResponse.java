package com.zync.network.user.api.model;

import com.zync.network.core.domain.ZID;

public record ProfileResponse(String name, String avatar, ZID id) {

}
