package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.security.User;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.post.application.payload.PostMapper;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostType;
import com.zync.network.post.infrastructure.repositories.PostJPARepository;
import com.zync.network.user.application.clients.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

import static com.zync.network.post.infrastructure.repositories.PostSpecification.byParentAndType;
@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindPostsReliesQueryHandler implements RequestHandler<FindPostsReliesQuery, List<PostPayLoad>> {
    UserClient userClient;
    PostJPARepository repository;
    PostMapper mapper;



    @Override
    public List<PostPayLoad> handle(FindPostsReliesQuery request) {
        Specification<Post> specification = byParentAndType(request.postId(), PostType.REPLY);
        Pageable page = PageUtils.of(request.limit(), request.offset());
        List<Post> posts =  repository.findAll(specification, page).getContent();
        return mapper.map(posts, request.self());
    }
}
