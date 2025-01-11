package com.zync.network.post.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.post.api.models.PostRequest;
import com.zync.network.post.application.command.*;
import com.zync.network.post.application.payload.PostPayLoad;
import com.zync.network.post.application.queries.FindPostsByIdsQuery;
import com.zync.network.post.application.queries.FindPostsReliesQuery;
import com.zync.network.post.domain.Visibility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final Mediator mediator;

    @PostMapping("")
    public ZID createThread(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody PostRequest postRequest) {
        return mediator.send(new CreatePostCommand(principal.id(), postRequest.content(), postRequest.mediaIds(), postRequest.visibility()));
    }

    @PostMapping("/{postId}/replies")
    public ZID reply(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody PostRequest postRequest, @PathVariable ZID postId) {
        return mediator.send(new ReplyCommand(principal.id(), postRequest.content(), postRequest.mediaIds(), postId, postRequest.visibility()));
    }


    @GetMapping("/{postId}/replies")
    public List<PostPayLoad> getReply(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable ZID postId, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new FindPostsReliesQuery(principal.id(), postId, limit, offset));
    }

    @GetMapping("/{postId}")
    public PostPayLoad getPost(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable ZID postId) {
        return mediator.send(new FindPostsByIdsQuery(Set.of(postId), principal.id())).stream().findFirst().orElseThrow();
    }

    @PostMapping("/{postId}/repost")
    public ZID repost(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody PostRequest postRequest, @PathVariable ZID postId) {
        return mediator.send(new RepostCommand(principal.id(), postId, postRequest.content(), postRequest.mediaIds(), postRequest.visibility()));
    }

    @PutMapping("/{id}/visibility")
    public void changeVisibility(
            @AuthenticationPrincipal JwtPrincipal principal,
            @PathVariable ZID id,
            @RequestParam Visibility visibility
    ) {
        mediator.send(new ChangeVisibilityCommand(id, principal.id(), visibility));
    }

    @PutMapping("/{id}")
    public void updatePost(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody PostRequest postRequest, @PathVariable ZID id) {
        mediator.send(new EditPostCommand(id, principal.id(), postRequest.content(), postRequest.mediaIds(), postRequest.visibility()));
    }

    @PostMapping("/{id}/like")
    public void like(@PathVariable ZID id, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new LikePostCommand(id, principal.id()));
    }

    @PostMapping("/{id}/unlike")
    public void unlike(@PathVariable ZID id, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new UnlikePostCommand(id, principal.id()));
    }



}
