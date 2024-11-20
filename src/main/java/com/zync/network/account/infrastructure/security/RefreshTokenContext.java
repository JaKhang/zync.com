package com.zync.network.account.infrastructure.security;

import com.zync.network.core.domain.ZID;
import lombok.Builder;
import lombok.Getter;

import java.time.temporal.ChronoUnit;

@Builder
@Getter
public class RefreshTokenContext {
    private ZID tokenId;
    private ZID accountId;
    private ZID deviceId;
    private int age;
    private ChronoUnit ageUnit;
}
