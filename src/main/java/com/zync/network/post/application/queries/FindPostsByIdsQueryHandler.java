package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.post.application.payload.PostMapper;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.Post;
import com.zync.network.post.infrastructure.repositories.PostJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindPostsByIdsQueryHandler implements RequestHandler<FindPostsByIdsQuery, Set<PostPayLoad>> {
    PostJPARepository repository;
    PostMapper mapper;
    @Override
    public Set<PostPayLoad> handle(FindPostsByIdsQuery request) {
        List<Post> posts = repository.findAllById(request.postIds());

        return new HashSet<>(mapper.map(posts, request.selfId()));
    }
}
