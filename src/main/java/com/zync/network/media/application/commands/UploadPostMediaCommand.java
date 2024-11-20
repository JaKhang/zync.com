package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Notification;
import com.zync.network.core.mediator.Request;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public record UploadPostMediaCommand(List<MultipartFile> mediaFiles, ZID userId) implements Request<Set<ZID>> {
}
