package com.zync.network.media.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaJpaRepository extends JpaRepository<Media, ZID> {
}
