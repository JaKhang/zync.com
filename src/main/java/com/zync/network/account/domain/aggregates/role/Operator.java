package com.zync.network.account.domain.aggregates.role;

import org.springframework.security.core.GrantedAuthority;

public enum Operator implements GrantedAuthority {
    CREATE,
    READ,
    UPDATE,
    DELETE;

    @Override
    public String getAuthority() {
        return "OPERATOR_" + name();
    }

    @Override
    public String toString() {
        return getAuthority();
    }
}
