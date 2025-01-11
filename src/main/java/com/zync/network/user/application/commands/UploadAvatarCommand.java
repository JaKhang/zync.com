package com.zync.network.user.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarCommand(MultipartFile file, ZID userId) implements MediatorRequest<ZID> {
}
