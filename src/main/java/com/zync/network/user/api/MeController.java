package com.zync.network.user.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.user.api.model.ProfileRequest;
import com.zync.network.user.api.model.UpdateProfileRequest;
import com.zync.network.user.application.commands.*;
import com.zync.network.user.application.models.ProfilePayload;
import com.zync.network.user.application.models.UserPayload;
import com.zync.network.user.application.queries.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MeController {
    Mediator mediator;

    // get profile
    // upload avatar
    // change avatar
    // change privacy

    @PutMapping("/privacy/private")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeUserPrivate(@AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new MakeUserPrivateCommand(principal.id()));
    }

    @PutMapping("/privacy/public")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeUserPublic(@AuthenticationPrincipal JwtPrincipal principal) {
        mediator.send(new MakeUserPublicCommand(principal.id()));
    }



    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profiles")
    public ProfilePayload getProfile(@AuthenticationPrincipal JwtPrincipal principal) {
        return mediator.send(new GetProfileByIdQuery(principal.id(), principal.id()));
    }

    @PostMapping("/profiles")
    public void updateProfile(@AuthenticationPrincipal JwtPrincipal principal, @RequestBody @Valid UpdateProfileRequest profileRequest) {
        mediator.send(new UpdateProfileCommand(principal.id(), profileRequest.links(), profileRequest.bio(), profileRequest.name()));
    }

    @GetMapping("")
    public UserPayload getMe(@AuthenticationPrincipal JwtPrincipal principal) {
        return mediator.send(new FindUserByIdQuery(principal.id(), principal.id()));
    }

    @PostMapping("/avatars")
    public ZID uploadAvatar(MultipartFile file, @AuthenticationPrincipal JwtPrincipal principal) {
        return mediator.send(new UploadAvatarCommand(file, principal.id()));
    }

    @PutMapping("/avatars")
    public void setAvatar(@RequestParam(name = "id") ZID avatarId, @AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new ChangeAvatarCommand(principal.id(), avatarId));
    }

    @GetMapping("/users/recommended")
    public List<UserPayload> getRecommendedUser(@AuthenticationPrincipal JwtPrincipal principal, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetRecommendedUserQuery(principal.id(), limit, offset));
    }

    @GetMapping("/users/followings")
    public List<UserPayload> getFollowings(@AuthenticationPrincipal JwtPrincipal principal, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetFollowingsQuery(principal.id(), principal.id(),limit, offset));
    }

    @GetMapping("/users/followers")
    public List<UserPayload> getFollowers(@AuthenticationPrincipal JwtPrincipal principal, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetFollowersQuery(principal.id(), principal.id(),limit, offset));
    }

    @GetMapping("/users/requested")
    public List<UserPayload> getRequested(@AuthenticationPrincipal JwtPrincipal principal, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "0") int offset) {
        return mediator.send(new GetRequestedUsersQuery(principal.id(),principal.id(), limit, offset));
    }

}
