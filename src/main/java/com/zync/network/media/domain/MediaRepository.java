package com.zync.network.media.domain;

import com.zync.network.core.domain.DomainRepository;
import com.zync.network.core.domain.ZID;

import java.util.List;

public interface MediaRepository extends DomainRepository<Media> {


    void deleteAll(List<ZID> ids);
}
