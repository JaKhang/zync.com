package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Request;
import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarCommand(
        MultipartFile file,
        ZID profileId
) implements Request<ZID> {
}
