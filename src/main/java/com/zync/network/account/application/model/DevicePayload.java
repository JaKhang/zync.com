package com.zync.network.account.application.model;


import com.zync.network.core.domain.ZID;

import java.sql.Timestamp;

public record DevicePayload(ZID id, String name, String os, String browser, Timestamp loginAt, boolean trusted) {
}
