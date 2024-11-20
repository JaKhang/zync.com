package com.zync.network.account.infrastructure.security;

import com.zync.network.core.domain.ZID;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Builder
@Getter
public class AccessTokenContext {
    private ZID accountId;
    private ZID tokenId;
    private int age;
    private ChronoUnit ageUnit;
    private Collection<? extends GrantedAuthority> authorities;
}
