package com.zync.network.user.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.user.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, ZID> {

    <T> T findById(ZID id, Class<T> classType);

    Page<User> findAll(Specification<User> specification, Pageable pageable);

}
