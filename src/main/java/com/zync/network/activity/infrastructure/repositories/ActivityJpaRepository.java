package com.zync.network.activity.infrastructure.repositories;

import com.zync.network.activity.domain.Activity;
import com.zync.network.core.domain.ZID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityJpaRepository extends JpaRepository<Activity, ZID> {
    Page<Activity> findAll(Specification<Activity> specification, Pageable pageable);
}
