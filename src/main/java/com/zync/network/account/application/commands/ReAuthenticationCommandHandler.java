package com.zync.network.account.application.commands;

import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.infrastructure.security.AccessTokenContext;
import com.zync.network.account.infrastructure.security.RefreshTokenAuthenticationProvider;
import com.zync.network.account.infrastructure.security.RefreshTokenAuthenticationToken;
import com.zync.network.account.infrastructure.security.TokenGenerator;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.temporal.ChronoUnit;

@Handler
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class ReAuthenticationCommandHandler implements RequestHandler<ReAuthenticateCommand, AuthenticationPayload> {


    final RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider;
    final TokenGenerator tokenGenerator;

    @Value("${application.security.jwt.access-token-age}")
    int accessTokenAge;

    @Value("${application.security.jwt.access-token-age-unit}")
    ChronoUnit accessTokenAgeUnit;

    @Override
    public AuthenticationPayload handle(ReAuthenticateCommand request) {
        log.info("handle command {}", request);
        Authentication authentication = refreshTokenAuthenticationProvider.authenticate(new RefreshTokenAuthenticationToken(request.refreshToken(), request.deviceName(), request.os(), request.browser()));
        Account account = (Account) authentication.getPrincipal();
        AccessTokenContext context = AccessTokenContext.builder()
                .tokenId(ZID.fast())
                .accountId(account.getId())
                .age(accessTokenAge)
                .ageUnit(accessTokenAgeUnit)
                .authorities(account.getAuthorities())
                .build();
        Jwt accessToken = tokenGenerator.generate(context);
        return new AuthenticationPayload(accessToken.getTokenValue(), "");
    }
}
