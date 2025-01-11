package com.zync.network.post.infrastructure.repositories;

import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class DefaultPostRepository implements PostRepository {
    PostJPARepository repository;
    @Override
    public Optional<Post> findById(ZID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(ZID id) {
        repository.deleteById(id);
    }

    @Override
    public ZID save(Post post) {
        return repository.save(post).getId();
    }
}
