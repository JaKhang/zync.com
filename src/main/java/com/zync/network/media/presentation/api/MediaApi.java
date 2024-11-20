package com.zync.network.media.presentation.api;

import com.zync.network.account.application.exceptions.MediaTypeNotValidException;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.media.application.commands.UploadAvatarCommand;
import com.zync.network.media.application.payload.ImageResponse;
import com.zync.network.media.application.queries.FindImageByIdQuery;
import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaApi {
    private final Mediator mediator;

    @PostMapping("/avatar")
    public ZID handleUploadAvatar(MultipartFile file, @AuthenticationPrincipal JwtPrincipal principal){
        MediaType type = MediaType.parseMediaType(Objects.requireNonNull(file.getContentType()));
        if (!MediaTypeUtil.isImage(type))
            throw new MediaTypeNotValidException();
        return mediator.send(new UploadAvatarCommand(
                file,
                principal.id()
        ));
    }

    @GetMapping("/images/{id}")
    public ImageResponse handleUploadAvatar(@PathVariable ZID id){
        return mediator.send(new FindImageByIdQuery(id));
    }
}
