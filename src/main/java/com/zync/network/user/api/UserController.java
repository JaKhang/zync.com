package com.zync.network.user.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.user.application.commands.RejectFollowRequestCommand;
import com.zync.network.user.api.model.RegisterRequest;
import com.zync.network.user.application.commands.*;
import com.zync.network.user.application.models.ProfilePayload;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.application.queries.*;
import com.zync.network.user.domain.user.Relationship;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    Mediator mediator;

    @PostMapping("/{targetId}/follow")
    public void follow(@PathVariable ZID targetId, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new FollowCommand(principal.id(), targetId));
    }

    @PostMapping("/{targetId}/unfollow")
    public Relationship unfollow(@PathVariable ZID targetId, @AuthenticationPrincipal JwtPrincipal principal) {
       return mediator.send(new UnfollowCommand(principal.id(), targetId));
    }

    @PostMapping("/{targetId}/accept-follow")
    public void acceptFollow(@PathVariable ZID targetId, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new AcceptFollowRequestCommand(targetId, principal.id()));
    }

    @PostMapping("/{targetId}/reject-follow")
    public void rejectFollow(@PathVariable ZID targetId, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new RejectFollowRequestCommand(principal.id(), targetId));
    }

    @PostMapping("/{targetId}/remove-request")
    public void removeFollowRequest(@PathVariable ZID targetId, @AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new RemoveFollowRequestCommand(targetId, principal.id()));
    }

    @PostMapping("/register")
    public ZID registerUser(@RequestBody @Valid RegisterRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(request);
        return mediator.send(new RegisterUserCommand(request.username(), request.firstName(), request.middleName(), request.lastName(), request.dateOfBirth(), locale, request.email(), request.password(), request.gender()));
    }

    @GetMapping("/{id}")
    public ProfilePayload getProfileById(@PathVariable ZID id, @AuthenticationPrincipal JwtPrincipal principal){
        return mediator.send(new GetProfileByIdQuery(principal.id(), id));
    }

    @GetMapping("/search")
    public List<UserPayload> search(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "FOLLOWING") Set<Relationship> relationships,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset
    ){
        return mediator.send(new SearchUsersQuery(keyword, relationships, principal.id(), limit, offset));
    }

    @GetMapping("/users/{id}/recommended")
    public List<UserPayload> getRecommendedUser(@AuthenticationPrincipal JwtPrincipal principal, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetRecommendedUserQuery(principal.id(), limit, offset));
    }

    @GetMapping("/users/{id}followings")
    public List<UserPayload> getFollowings(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable ZID id,@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetFollowingsQuery(principal.id(), id,limit, offset));
    }

    @GetMapping("/users/{id}/followers")
    public List<UserPayload> getFollowers(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable ZID id,@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetFollowersQuery(principal.id(), id,limit, offset));
    }

    @GetMapping("/users/{id}/requested")
    public List<UserPayload> getRequested(@AuthenticationPrincipal JwtPrincipal principal, @PathVariable ZID id,@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetRequestedUsersQuery(principal.id(), id, limit, offset));
    }
}
