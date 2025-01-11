package com.zync.network.user.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.User;
import com.zync.network.user.domain.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DomainUserRepository implements UserRepository {
    UserJpaRepository repository;
    @Override
    public Optional<User> findById(ZID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(User user) {
        return repository.save(user).getId();
    }
}
