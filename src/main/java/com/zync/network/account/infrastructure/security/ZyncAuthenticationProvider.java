package com.zync.network.account.infrastructure.security;

import com.zync.network.account.domain.aggregates.account.Account;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class ZyncAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        if (userDetails instanceof Account account){
            if (account.isTwoFactorEnabled()) {
                String code = (String) authentication.getDetails();
            }
        }
    }
}
