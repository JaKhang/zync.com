package com.zync.network.media.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJPARepository extends JpaRepository<Image, ZID> {
    <T>T findById(ZID id, Class<T> classType);
}
