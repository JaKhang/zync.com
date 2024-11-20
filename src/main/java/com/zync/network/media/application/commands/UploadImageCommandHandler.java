package com.zync.network.media.application.commands;

import com.zync.network.account.application.exceptions.MediaProcessingException;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.application.events.ImageUploadedEvent;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.domain.Variant;
import com.zync.network.media.infrastructure.processor.ImageProcessor;
import com.zync.network.media.infrastructure.storage.FilePermission;
import com.zync.network.media.infrastructure.storage.StorageException;
import com.zync.network.media.infrastructure.storage.StorageProvider;
import com.zync.network.media.infrastructure.storage.StoreArg;
import com.zync.network.media.infrastructure.utils.FileNameUtils;
import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import com.zync.network.media.infrastructure.worker.AsyncWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Handler
@RequiredArgsConstructor
@Log4j2
public class UploadImageCommandHandler implements RequestHandler<UploadImageCommand, ZID> {
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


    public ZID handle(UploadImageCommand request) {
        // save to database
        final ZID id = mediator.send(new CreateImageCommand(ZID.fast(), request.userId(), List.of(), 0, "", false, null, MediaType.parseMediaType(request.file().getContentType())));

        // save to tmp
        Path src = this.storeToTmp(request.file(), id);

        // process
        this.processAndUploadImageAsync(src, id, request.userId(), request.dimension(), request.variants());

        return id;
    }

    private Path storeToTmp(MultipartFile file, ZID id) {
        String fileName = FileNameUtils.changeBaseName(file.getOriginalFilename(), id.toLowerCase());
        try (InputStream is = file.getInputStream()) {
            return storageProvider.saveToTmp(fileName, is);
        } catch (IOException e) {
            throw new StorageException("");
        }
    }



    public void processAndUploadImageAsync(final Path src, final ZID fileId, final ZID userId, Dimension dimension, List<Dimension> dimensions ) {
        asyncWorker.doWorkAsync(() -> {

            FilePermission permission = FilePermission.builder()
                    .executable(true)
                    .readable(true)
                    .writeable(true)
                    .build();
            String path = new StringJoiner("/")
                    .add(imagePath)
                    .add(fileId.toLowerCase() + "." + avatarFormat)
                    .toString();
            Path resizeImage = resize(src, dimension.width(), dimension.height());
            String reference = store(resizeImage, path, permission);

            List<Variant> variants = new ArrayList<>();
            for (Dimension d : dimensions){
                path = new StringJoiner("/")
                        .add(imagePath)
                        .add(ZID.fast().toLowerCase() + "." + avatarFormat)
                        .toString();
                Path s = resize(src, d.width(), d.height());
                String ref = store(s, path, permission);
                variants.add(new Variant(reference, d));
                s.toFile().delete();
            }
            src.toFile().delete();
            eventPublisher.publishEvent(new ImageUploadedEvent(
                    fileId,
                    userId,
                    variants,
                    src.toFile().length(),
                    reference,
                    new Dimension(avatarWidth, avatarHeight),
                    MediaTypeUtil.getMediaTypeForFileName(avatarFormat)
            ));

        });
    }



    private Path resize(Path src, int width, int height){
        try {
            return imageProcessor.resize(new Dimension(avatarWidth, avatarHeight), src, avatarFormat);
        } catch (Exception exception) {
            throw new MediaProcessingException();
        } finally {
        }
    }

    private String store(Path src, String path, FilePermission permission) {
        try (InputStream is = new FileInputStream(src.toFile())) {
            log.info("Image path {}", path);
            var arg = StoreArg.builder()
                    .path(path)
                    .stream(is)
                    .size(src.toFile().length())
                    .permission(permission)
                    .contentType("image/" + avatarFormat)
                    .build();
            return storageProvider.store(arg);
        } catch (IOException ioException) {
            throw new MediaProcessingException();
        } finally {
            src.toFile().delete();
        }
    }
}
