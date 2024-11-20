package com.zync.network.media.application.commands;

import com.zync.network.account.application.exceptions.MediaProcessingException;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.events.ImageUploadedEvent;
import com.zync.network.media.application.events.MediaProcessingFailedEvent;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.infrastructure.processor.ImageProcessor;
import com.zync.network.media.infrastructure.storage.FilePermission;
import com.zync.network.media.infrastructure.storage.StorageException;
import com.zync.network.media.infrastructure.storage.StorageProvider;
import com.zync.network.media.infrastructure.storage.StoreArg;
import com.zync.network.media.infrastructure.utils.FileNameUtils;
import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import com.zync.network.media.infrastructure.worker.AsyncWorker;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Handler
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UploadAvatarCommandHandler implements RequestHandler<UploadAvatarCommand, ZID> {
    private final StorageProvider storageProvider;
    private final ImageProcessor imageProcessor;
    private final ApplicationEventPublisher eventPublisher;
    private final Mediator mediator;
    private final AsyncWorker asyncWorker;

    @Value("${application.media.image.avatar.width}")
    private int avatarWidth;

    @Value("${application.media.image.avatar.height}")
    private int avatarHeight;

    @Value("${application.media.image.avatar.format}")
    private String avatarFormat;
    @Value("${application.media.image.path}")
    private String imagePath;


    public ZID handle(UploadAvatarCommand request) {
        // save to database
        final ZID id = mediator.send(new CreateImageCommand(ZID.fast(), request.profileId(), List.of(), 0, "", false, null, MediaType.parseMediaType(request.file().getContentType())));

        // generate filename
        String fileName = FileNameUtils.changeBaseName(request.file().getOriginalFilename(), id.toLowerCase());
        Path src = null;
        try (InputStream is = request.file().getInputStream()) {
            //save to tmp
            src = storageProvider.saveToTmp(fileName, is);
        } catch (IOException e) {
            throw new StorageException("");
        }

        log.info("Test Async {}", Thread.currentThread().getName());
        processAndUploadImageAsync(src, id, request.profileId());
        return id;
    }


    public void processAndUploadImageAsync(final Path src, final ZID fileId, final ZID userId) {
        asyncWorker.doWorkAsync(() -> {
            Path resizedImage = null;
            try {
                resizedImage = imageProcessor.resize(new Dimension(avatarWidth, avatarHeight), src, avatarFormat);
            } catch (Exception exception) {
                eventPublisher.publishEvent(new MediaProcessingFailedEvent(List.of(fileId)));
                throw new MediaProcessingException();
            } finally {
                src.toFile().delete();
            }
            FilePermission permission = FilePermission.builder()
                    .executable(true)
                    .readable(true)
                    .writeable(true)
                    .build();
            String path = new StringJoiner("/")
                    .add(imagePath)
                    .add(fileId.toLowerCase() + "." + avatarFormat)
                    .toString();

            long size = resizedImage.toFile().length();
            try (InputStream is = new FileInputStream(resizedImage.toFile())) {
                log.info("Image path {}", path);
                var arg = StoreArg.builder()
                        .path(path)
                        .stream(is)
                        .size(size)
                        .permission(permission)
                        .contentType("image/" + avatarFormat)
                        .build();
                String reference = storageProvider.store(arg);


                eventPublisher.publishEvent(new ImageUploadedEvent(
                        fileId,
                        userId,
                        List.of(),
                        size,
                        reference,
                        new Dimension(avatarWidth, avatarHeight),
                        MediaTypeUtil.getMediaTypeForFileName(avatarFormat)
                ));
            } catch (IOException ioException) {
                eventPublisher.publishEvent(new MediaProcessingFailedEvent(List.of(fileId)));
                throw new MediaProcessingException();
            } finally {
                resizedImage.toFile().delete();
            }
        });

    }
}
