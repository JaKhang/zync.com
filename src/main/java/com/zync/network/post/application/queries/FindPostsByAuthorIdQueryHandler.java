package com.zync.network.post.application.queries;

import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.post.application.payload.PostMapper;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.Visibility;
import com.zync.network.post.infrastructure.repositories.PostJPARepository;
import com.zync.network.user.application.clients.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static com.google.common.base.Predicates.and;
import static com.zync.network.post.infrastructure.repositories.PostSpecification.checkVisibility;

@Handler
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FindPostsByAuthorIdQueryHandler implements RequestHandler<FindPostsByAuthorIdQuery, List<PostPayLoad>> {
    PostMapper mapper;
    PostJPARepository repository;
    UserClient userClient;

    @Override
    public List<PostPayLoad> handle(FindPostsByAuthorIdQuery request) {
        boolean isFollowing = userClient.isFollowing(request.userId(), request.sefId());
        boolean isSelf = request.sefId().equals(request.userId());

        Sort sort = Sort.by(Sort.Direction.DESC, "pin", "createdAt");
        Pageable pageable = PageUtils.of(request.limit(), request.offset(), sort);

        Specification<Post> visibleSpec = checkVisibility(isFollowing, isSelf, false,request.sefId());
        Specification<Post> specification = (root, query, builder) -> builder.and(
                root.get("type").in(request.types()),
                builder.equal(root.get("authorId"), request.userId())


        );
        specification = specification.and(visibleSpec);
        List<Post> posts = repository.findAll(specification, pageable).getContent();
        return mapper.map(posts, request.sefId());
    }
}
