package com.zync.network.core.security;

import com.zync.network.core.domain.ZID;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtPrincipal(ZID id,
                           Collection<? extends GrantedAuthority> authorities,
                           ZID deviceId) {


}
