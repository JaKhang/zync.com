package com.zync.network.post.application.queries;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.core.utils.PageUtils;
import com.zync.network.post.application.payload.PostMapper;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.Visibility;
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
public class FindRecommendedPostsQueryHandler implements RequestHandler<FindRecommendedPostsQuery, List<PostPayLoad>> {
    UserClient userClient;
    PostJPARepository repository;
    PostMapper mapper;

    //data: data +
    //hasMore:
    //limit = 10
    //page: 1
    //has false




    @Override
    public List<PostPayLoad> handle(FindRecommendedPostsQuery request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageUtils.of(request.limit(), request.offset(), sort);
        Specification<Post> specification = (root, query, builder) -> builder.and(
            builder.notEqual(root.get("authorId"), request.self()),
            builder.equal(root.get("visibility"), Visibility.ANY) ,
            builder.not(root.get("deleted"))
        ) ;

        var rs = repository.findAll(specification, pageable).stream().toList();
        return mapper.map(rs, request.self());
    }
}