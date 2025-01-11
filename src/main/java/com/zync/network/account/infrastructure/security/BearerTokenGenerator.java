package com.zync.network.account.infrastructure.security;

import com.zync.network.core.domain.ZID;import com.zync.network.core.security.JwtPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class BearerTokenGenerator implements TokenGenerator {
    private static final String ISSUER = "zync";
    private static final String TOKEN_TYPE_CLAIM_NAME = "type";
    private static final String AUTHORITIES_CLAIM_NAME = "authorities";
    private static final String DEVICE_CLAIM_NAME = "device";

    JwtEncoder jwtEncoder;


    @Override
    public Jwt generate(JwtPrincipal account, TokenType type) {
        JwtClaimsSet claimsSet = switch (type) {
            case ACCESS -> JwtClaimsSet.builder()
                    .issuer("zync")
                    .claim("type", type.toString())
                    .subject(account.id().toString())
                    .expiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
                    .issuedAt(Instant.now())
                    .id(ZID.fast().toString())
                    .claim("authorities", account.authorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                    .build();
            case REFRESH -> JwtClaimsSet.builder()
                    .issuer("zync")
                    .claim("type", type.toString())
                    .claim("device", account.deviceId().toString())
                    .subject(account.id().toString())
                    .expiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                    .issuedAt(Instant.now())
                    .id(ZID.fast().toString())
                    .build();
        };
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet));
    }

    @Override
    public Jwt generate(AccessTokenContext context) {
        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .claim(TOKEN_TYPE_CLAIM_NAME, TokenType.ACCESS)
                .claim(AUTHORITIES_CLAIM_NAME,context.getAuthorities())
                .subject(context.getAccountId().toString())
                .expiresAt(Instant.now().plus(context.getAge(), context.getAgeUnit()))
                .issuedAt(Instant.now())
                .id(context.getTokenId().toString())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }

    @Override
    public Jwt generate(RefreshTokenContext context) {
        var claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .claim(TOKEN_TYPE_CLAIM_NAME, TokenType.REFRESH)
                .claim(DEVICE_CLAIM_NAME, context.getDeviceId().toString())
                .subject(context.getAccountId().toString())
                .expiresAt(Instant.now().plus(context.getAge(), context.getAgeUnit()))
                .issuedAt(Instant.now())
                .id(context.getTokenId().toString())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims));
    }
}
