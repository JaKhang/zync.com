package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.post.application.payload.PostMapper;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.Post;
import com.zync.network.post.infrastructure.repositories.PostJPARepository;
import com.zync.network.user.application.clients.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;

import static com.zync.network.post.infrastructure.repositories.PostSpecification.checkVisibility;

@Handler
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class FindPostsOfFollowingQueryHandler implements RequestHandler<FindPostsOfFollowingQuery, List<PostPayLoad>> {
    UserClient userClient;
    PostJPARepository repository;
    PostMapper mapper;

    @Override
    public List<PostPayLoad> handle(FindPostsOfFollowingQuery request) {

        Set<ZID> followingIds = userClient.findFollowingIds(request.userId());
        Sort sort = Sort.by(Sort.Direction.DESC,  "createdAt");
        Pageable pageable = PageUtils.of(request.limit(), request.offset(), sort);
        Specification<Post> followingUserCheck = (root, query, builder) -> root.get("authorId").in(followingIds);
        Specification<Post> specification = (root, query, builder) -> builder.and(root.get("type").in(request.types()));
        Specification<Post> checkVisibility = checkVisibility(true, false, false, request.userId());

        specification = specification.and(followingUserCheck).and(checkVisibility);
        List<Post> posts = repository.findAll(specification, pageable).getContent();
        return mapper.map(posts, request.userId());
    }
}
