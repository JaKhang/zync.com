package com.zync.network.account.infrastructure.persistence;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.account.domain.repositories.AccountRepository;
import com.zync.network.core.domain.ZID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountDomainRepository implements AccountRepository {
    AccountJpaRepository accountJpaRepository;

    @Override
    public boolean existsByEmail(String email) {
        return accountJpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<Account> findByUsernameOrEmail(String usernameOrEmail) {
        return accountJpaRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }

    @Override
    public boolean existsByUsername(String username) {
        // TODO (PC, 29/11/2024): To change the body of an implemented method
        return accountJpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountJpaRepository.findByUsernameOrEmail(email, email);
    }

    @Override
    public ZID save(Account account) {
        // TODO (PC, 08/10/2024): To change the body of an implemented method
        return accountJpaRepository.save(account).getId();
    }

    @Override
    public Optional<Account> findById(ZID zid) {
        return accountJpaRepository.findById(zid);
    }
}
