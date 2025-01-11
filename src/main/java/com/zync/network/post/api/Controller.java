package com.zync.network.post.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.application.queries.FindPostsByAuthorIdQuery;
import com.zync.network.post.domain.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class Controller {

    private final Mediator mediator;
    @GetMapping("/{id}/posts")
    List<PostPayLoad> findMyPosts(@AuthenticationPrincipal JwtPrincipal principal,
                                  @RequestParam(defaultValue = "10") int limit,
                                  @RequestParam(defaultValue = "0") int offset,
                                  @RequestParam(defaultValue = "POST,REPOST") Set<PostType> types,
                                  @PathVariable ZID id
    ) {
        return mediator.send(new FindPostsByAuthorIdQuery(id, principal.id(), limit, offset, types));
    }
}
