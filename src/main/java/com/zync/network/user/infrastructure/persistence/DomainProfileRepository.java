package com.zync.network.user.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.profile.Profile;
import com.zync.network.user.domain.profile.ProfileRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DomainProfileRepository implements ProfileRepository {
    ProfileJpaRepository repository;
    @Override
    public Optional<Profile> findById(ZID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(Profile profile) {
        return repository.save(profile).getId();
    }
}
