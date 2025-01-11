package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.MediatorRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UploadAllMediaCommand(List<MultipartFile> mediaFiles, ZID userId) implements MediatorRequest<List<ZID>> {
}
