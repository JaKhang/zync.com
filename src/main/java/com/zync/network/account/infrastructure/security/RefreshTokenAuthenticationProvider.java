package com.zync.network.account.infrastructure.security;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.aggregates.account.Device;
import com.zync.network.account.domain.exception.InvalidBearerTokenException;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.core.domain.ZID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;


@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class RefreshTokenAuthenticationProvider implements AuthenticationProvider {
    private static final String TOKEN_TYPE_CLAIM_NAME = "type";
    JwtDecoder decoder;
    AccountRepository accountRepository;
    UserDetailsChecker postAuthenticationChecker = new DefaultPostAuthenticationChecks();
    UserDetailsChecker preAuthenticationChecker = new DefaultPreAuthenticationChecks();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RefreshTokenAuthenticationToken token = (RefreshTokenAuthenticationToken) authentication;
        Jwt jwt = convertToJwt(token.getToken());
        if (!jwt.getClaimAsString(TOKEN_TYPE_CLAIM_NAME).equals(TokenGenerator.TokenType.REFRESH.name()))
            throw new BadCredentialsException("Refresh token is invalid");
        ZID id = ZID.from(jwt.getSubject());

        Account account = accountRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
        preAuthenticationChecker.check(account);
        postAuthenticationChecker.check(account);

        ZID deviceId = ZID.from(jwt.getClaimAsString("device"));
        account.validateDevice(deviceId, token.getOs(), token.getBrowser());
        return authenticateSuccess(jwt, account);
    }

    private Jwt convertToJwt(String token) {
        try {
            return decoder.decode(token);
        } catch (JwtException jwtException) {
            throw new InvalidBearerTokenException();
        }
    }

    private Authentication authenticateSuccess(Jwt jwt, Account account) {
        return new RefreshTokenAuthenticationToken(jwt.getTokenValue(), account);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return RefreshTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        private DefaultPreAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("Failed to authenticate since user account is locked");
                throw new LockedException("User account is locked");
            } else if (!user.isEnabled()) {
                log.debug("Failed to authenticate since user account is disabled");
                throw new DisabledException("User is disabled");
            } else if (!user.isAccountNonExpired()) {
                log.debug("Failed to authenticate since user account has expired");
                throw new AccountExpiredException("User account has expired");
            }
        }
    }

    private static class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        private DefaultPostAuthenticationChecks() {
        }

        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException("User credentials have expired");
            }
        }
    }
}
