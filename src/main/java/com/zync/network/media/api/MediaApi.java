package com.zync.network.media.api;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.security.JwtPrincipal;
import com.zync.network.media.application.commands.UploadAllMediaCommand;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.media.application.queries.FindMediaByIdsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaApi {
    private final Mediator mediator;

    @PostMapping
    public List<ZID> uploadMedia(List<MultipartFile> files, @AuthenticationPrincipal JwtPrincipal principal){
        return mediator.send(new UploadAllMediaCommand(files, principal.id()));
    }

    @GetMapping
    public List<MediaPayLoad> findMedia(@RequestParam List<ZID> ids){
        return mediator.send(new FindMediaByIdsQuery(ids));
    }

}
