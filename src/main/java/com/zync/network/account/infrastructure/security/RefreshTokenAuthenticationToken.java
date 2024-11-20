package com.zync.network.account.infrastructure.security;

import com.zync.network.account.domain.aggregates.account.Account;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;


@Getter
public class RefreshTokenAuthenticationToken extends AbstractAuthenticationToken {
    private final String token;
    private Account account;
    private String deviceName;
    private String os;
    private String browser;
    public RefreshTokenAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
    }

    public RefreshTokenAuthenticationToken(String token, String deviceName, String os, String browser) {
        super(Collections.emptyList());
        this.token = token;
        this.deviceName = deviceName;
        this.os = os;
        this.browser = browser;
    }

    public RefreshTokenAuthenticationToken(String token, Account account) {
        super(account.getAuthorities());
        this.token = token;
        this.account = account;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }
}
