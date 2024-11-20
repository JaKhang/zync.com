package com.zync.network.media.infrastructure.persistence;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ImageDomainRepository implements ImageRepository {
    private final ImageJPARepository repository;
    private final ImageJPARepository imageJPARepository;

    @Override
    public Optional<Image> findById(ZID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(Image image) {
        image = imageJPARepository.save(image);
        return image.getId();
    }
}
