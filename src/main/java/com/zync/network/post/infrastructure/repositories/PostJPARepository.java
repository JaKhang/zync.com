package com.zync.network.post.infrastructure.repositories;

import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJPARepository extends JpaRepository<Post, ZID> {
    int count(Specification<Post> specification);

    Page<Post> findAll(Specification<Post> specification, Pageable pageable);

    List<Post> findAll(Specification<Post> specification);

    @Query("SELECT p.id FROM Post p WHERE p.parentId = :parentId AND p.type = :type")
    List<ZID> findIdsByParentId(ZID parentId, PostType type);
}
