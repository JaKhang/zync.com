package com.zync.network.account.infrastructure.persistence;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.core.domain.ZID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<Account, ZID> {
    boolean existsByEmail(String email);

    Optional<Account> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    Optional<Account> findByEmail(String email);
}
