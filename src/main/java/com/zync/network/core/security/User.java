package com.zync.network.core.security;

import com.zync.network.core.domain.ZID;import lombok.Builder;
import lombok.Getter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.*;

@Builder
@Getter
public class User implements UserDetails{
    @Serial
    private static final long serialVersionUID = 620L;
    private ZID id;
    private String password;
    private final String email;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonLocked;
    private final boolean enabled;

    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean equals(Object obj) {
        if (obj instanceof User user) {
            return this.id.equals(user.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        return this.getClass().getName() + " [" +
                "Email=" + this.email + ", " +
                "Password=[PROTECTED], " +
                "Enabled=" + this.enabled + ", " +
                "AccountNonLocked=" + this.accountNonLocked + ", " +
                "Granted Authorities=" + this.authorities + "]";
    }
}
