package com.zync.network.media.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Media;
import com.zync.network.media.domain.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MediaDomainRepository implements MediaRepository {
    private final MediaJpaRepository repository;

    @Override
    public void deleteAll(List<ZID> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public Optional<Media> findById(ZID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(Media media) {
        return repository.save(media).getId();
    }
}
