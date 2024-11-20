package com.zync.network.user.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJpaRepository extends JpaRepository<Profile, ZID> {

    <T> T findById(ZID id, Class<T> classType);

}
