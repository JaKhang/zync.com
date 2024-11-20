package com.zync.network.account.domain.repositories;

import com.zync.network.account.domain.aggregates.account.Account;
import com.zync.network.core.domain.ZID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository  {
    boolean existsByEmail(String email);


    Optional<Account> findByEmail(String email);

    ZID save(Account account);

    Optional<Account> findById(ZID zid);
}