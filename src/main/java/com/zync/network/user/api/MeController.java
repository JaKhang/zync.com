package com.zync.network.user.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.media.application.commands.UploadAvatarCommand;
import com.zync.network.user.api.model.ProfileRequest;
import com.zync.network.user.api.model.ProfileResponse;
import com.zync.network.user.application.commands.ChangeAvatarCommand;
import com.zync.network.user.application.commands.CreateProfileCommand;
import com.zync.network.user.application.models.ProfileProjection;
import com.zync.network.user.application.queries.GetProfileByIdQuery;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/me")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MeController {
    Mediator mediator;

    // update bio
    // add link
    // remove link
    // get profile
    // create profile

    @PostMapping("/profiles")
    public ZID handleCreateProfile(@RequestBody ProfileRequest request){
        Locale locale = LocaleContextHolder.getLocale();
        return mediator.send(new CreateProfileCommand(request.firstName(), request.lastName(), request.middleName(), request.dateOfBirth(), locale, request.email(), request.password()));
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profiles")
    public ProfileProjection handleGetProfile(@AuthenticationPrincipal JwtPrincipal principal){
        Locale locale = LocaleContextHolder.getLocale();
        return mediator.send(new GetProfileByIdQuery(principal.id()));
    }

    @PostMapping("/avatars")
    public ZID handleUploadAvatar(MultipartFile file, @AuthenticationPrincipal JwtPrincipal principal){
        return mediator.send(new UploadAvatarCommand(file, principal.id()));
    }

    @PutMapping("/avatars")
    public void setAvatar(@RequestParam(name = "id") ZID avatarId, @AuthenticationPrincipal JwtPrincipal principal){
        mediator.send(new ChangeAvatarCommand(principal.id(), avatarId));
    }

}
