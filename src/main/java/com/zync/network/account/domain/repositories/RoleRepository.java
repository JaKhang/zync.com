package com.zync.network.account.domain.repositories;

import com.zync.network.core.domain.ZID;import com.zync.network.account.domain.aggregates.role.Role;
import com.zync.network.account.domain.aggregates.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, ZID>{
    boolean existsByName(RoleName name);

    Role findByName(RoleName roleName);

    @Query("select r from Role r where r.name in :roleNames")
    Set<Role> findAllByNames(Iterable<RoleName> roleNames);
}

