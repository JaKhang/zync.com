package com.zync.network.account.infrastructure.security;

import com.zync.network.core.domain.ZID;import com.zync.network.account.domain.exception.AccountAlreadyVerifiedException;
import com.zync.network.account.domain.exception.InvalidBearerTokenException;
import com.zync.network.core.security.JwtPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

@Slf4j
public class JwtPrincipalConvertor implements Converter<Jwt, AbstractAuthenticationToken> {
    private final JwtGrantedAuthoritiesConverter authoritiesConverter;

    public JwtPrincipalConvertor() {
        this.authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("authorities");
    }

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        if ("access".equals(source.getClaimAsString("type")))
            throw new InvalidBearerTokenException();
        log.info(source.toString());
        ZID id = null;
        ZID deviceId = null;
        Collection<? extends GrantedAuthority> authorities = null;
        try {
            id = ZID.from(source.getSubject());
            authorities = authoritiesConverter.convert(source);
            JwtPrincipal jwtPrincipal = new JwtPrincipal(id, authorities, null);
            return new UsernamePasswordAuthenticationToken(jwtPrincipal, source, authorities);
        } catch (RuntimeException e) {
            throw new AccountAlreadyVerifiedException();
        }
    }
}
