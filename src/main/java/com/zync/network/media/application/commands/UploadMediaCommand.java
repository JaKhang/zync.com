package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import org.springframework.web.multipart.MultipartFile;

public record UploadMediaCommand(ZID userId, MultipartFile file) implements MediatorRequest<ZID> {
}
