package com.zync.network.account.domain.aggregates.role;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.ZID;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends AggregateRoot {
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    RoleName name;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<Operator> operators;

    public List<? extends GrantedAuthority> authorities(){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(operators);
        grantedAuthorities.add(name);
        return grantedAuthorities;

    }

    public Role(ZID id, RoleName name, Set<Operator> operators) {
        super(id);
        this.name = name;
        this.operators = operators;
    }

    public Role(ZID id) {
        super(id);
    }
}
