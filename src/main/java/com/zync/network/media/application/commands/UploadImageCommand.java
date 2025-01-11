package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import com.zync.network.media.domain.Dimension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadImageCommand(
        MultipartFile file,
        ZID userId,
        Dimension dimension,
        List<Dimension> variants
) implements MediatorRequest<ZID> {

}
