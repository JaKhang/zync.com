package com.zync.network.account.infrastructure.security;

import com.zync.network.core.security.JwtPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

public interface TokenGenerator {
    Jwt generate(JwtPrincipal user, TokenType type);

    Jwt generate(AccessTokenContext context);

    Jwt generate(RefreshTokenContext context);


    enum TokenType{
        ACCESS,
        REFRESH
    }
}
