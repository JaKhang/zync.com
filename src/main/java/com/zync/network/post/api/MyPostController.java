package com.zync.network.post.api;

import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.application.queries.FindPostsByAuthorIdQuery;
import com.zync.network.post.application.queries.FindPostsOfFollowingQuery;
import com.zync.network.post.application.queries.FindRecommendedPostsQuery;
import com.zync.network.post.domain.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MyPostController {
    private final Mediator mediator;

    @GetMapping("/posts")
    List<PostPayLoad> findMyPosts(@AuthenticationPrincipal JwtPrincipal principal,
                                  @RequestParam(defaultValue = "1") int limit,
                                  @RequestParam(defaultValue = "0") int offset,
                                  @RequestParam(defaultValue = "POST,REPOST") Set<PostType> types
    ) {
        return mediator.send(new FindPostsByAuthorIdQuery(principal.id(), principal.id(), limit, offset, types));
    }

    @GetMapping("/posts/followings")
    List<PostPayLoad> findFollowingUsersPosts(@AuthenticationPrincipal JwtPrincipal principal,
                                              @RequestParam(defaultValue = "1") int limit,
                                              @RequestParam(defaultValue = "0") int offset,
                                              @RequestParam(defaultValue = "POST,REPOST") Set<PostType> types
    ) {
        return mediator.send(new FindPostsOfFollowingQuery(principal.id(), limit, offset, types));
    }

    @GetMapping("/posts/recommended")
    List<PostPayLoad> findRecommendedUsersPosts(@AuthenticationPrincipal JwtPrincipal principal,
                                              @RequestParam(defaultValue = "1") int limit,
                                              @RequestParam(defaultValue = "0") int offset
    ) {
        return mediator.send(new FindRecommendedPostsQuery(principal.id(), limit, offset));
    }
}
