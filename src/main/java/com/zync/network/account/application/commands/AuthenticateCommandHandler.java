package com.zync.network.account.application.commands;

import com.zync.network.account.application.model.AuthenticationPayload;
import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.aggregates.account.Device;
import com.zync.network.account.domain.events.AuthenticatedEvent;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.account.infrastructure.security.AccessTokenContext;
import com.zync.network.account.infrastructure.security.RefreshTokenContext;
import com.zync.network.account.infrastructure.security.TokenGenerator;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.security.JwtPrincipal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Log4j2
public class AuthenticateCommandHandler implements RequestHandler<AuthenticateCommand, AuthenticationPayload> {
    ApplicationEventPublisher eventPublisher;
    AccountRepository accountRepository;
    TokenGenerator tokenGenerator;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationPayload handle(AuthenticateCommand command) {
        Account account = authenticate(command.email(), command.password(), command.twoFactorCode());
        ZID deviceId = detectDevice(account, command.ip(), command.os(), command.browser());
        AccessTokenContext accessTokenContext = AccessTokenContext.builder()
                .age(30)
                .ageUnit(ChronoUnit.DAYS)
                .tokenId(ZID.fast())
                .accountId(account.getId())
                .authorities(account.getAuthorities())
                .build();
        RefreshTokenContext refreshTokenContext = RefreshTokenContext.builder()
                .deviceId(deviceId)
                .accountId(account.getId())
                .age(30)
                .ageUnit(ChronoUnit.DAYS)
                .tokenId(ZID.fast())
                .build();
        Jwt accessToken = tokenGenerator.generate(accessTokenContext);
        Jwt refreshToken = tokenGenerator.generate(refreshTokenContext);
        eventPublisher.publishEvent(new AuthenticatedEvent(account.getId(), command.os(), command.browser()));
        return new AuthenticationPayload(accessToken.getTokenValue(), refreshToken.getTokenValue());
    }

    private ZID detectDevice(Account account, String ip, String os, String browser) {
        ZID deviceId = null;
        if (account.containsDevice(os, browser)) {
            Device device = account.getDevice(os, browser);
            deviceId = device.getId();
            device.setLoginAt(LocalDateTime.now());
        } else {
            deviceId = account.registerDevice(os, browser, ip);
        }
        accountRepository.save(account);
        return deviceId;
    }

    private Account authenticate(String email, String password, @Nullable String twoFactorCode) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found account with email"));

        if (!passwordEncoder.matches(password, account.getPassword()))
            throw new BadCredentialsException("Password is Invalid !");
        if (account.isLocked())
            throw new LockedException("Account was locked !");
        if (!account.isVerified())
            throw new DisabledException("Account is not verify");
        if (account.isTwoFactorEnabled()) {
            twoStepAuthenticate(account, twoFactorCode);
        }
        return account;
    }

    private void twoStepAuthenticate(Account account, String twoFactorCode) {

    }
}
