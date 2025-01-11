package com.zync.network.media.application.commands;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.core.mediator.RequestHandler;
import com.zync.network.media.domain.*;
import com.zync.network.media.infrastructure.factories.MediaFactory;
import com.zync.network.media.infrastructure.processor.ImageProcessor;
import com.zync.network.media.infrastructure.processor.MediaStandardizer;
import com.zync.network.media.infrastructure.storage.FilePermission;
import com.zync.network.media.infrastructure.storage.StorageException;
import com.zync.network.media.infrastructure.storage.StorageProvider;
import com.zync.network.media.infrastructure.storage.StoreArg;
import com.zync.network.media.infrastructure.utils.FFmpegUtils;
import com.zync.network.media.infrastructure.utils.FileNameUtils;
import com.zync.network.media.infrastructure.utils.MediaTypeUtil;
import com.zync.network.media.infrastructure.worker.AsyncWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@RequiredArgsConstructor
@Handler
public class UploadMediaCommandHandler implements RequestHandler<UploadMediaCommand, ZID> {
    private final StorageProvider storageProvider;
    private final ImageProcessor imageProcessor;
    private final ApplicationEventPublisher eventPublisher;
    private final Mediator mediator;
    private final AsyncWorker asyncWorker;
    private final MediaFactory factory;
    private final MediaRepository mediaRepository;
    private final MediaStandardizer mediaStandardizer;



    @Value("${application.media.audio.path}")
    private String audioPath;

    @Value("${application.media.video.path}")
    private String videoPath;
    @Value("${application.media.image.path}")
    private String imagePath;

    @Override
    @Transactional

    public ZID handle(UploadMediaCommand request) {
        Media media = factory.create(request.userId(), request.file());
        // save to database
        final ZID id = mediaRepository.save(media);

        // save to tmp
        String fileName = FileNameUtils.changeBaseName(request.file().getOriginalFilename(), id.toLowerCase());
        Path path = null;
        try (InputStream is = request.file().getInputStream()) {
             path = storageProvider.saveToTmp(fileName, is);
        } catch (IOException e) {
            throw new StorageException("");
        }

        processAndUpload(path, id);

        return id;
    }

    private void processAndUpload( Path path, ZID id) {
        asyncWorker.doWorkAsync(() -> {
            Path newpath = mediaStandardizer.standardize(id, path);
            String tmpFileName = path.toFile().getName();

            FilePermission permission = FilePermission.builder()
                    .executable(true)
                    .readable(true)
                    .writeable(true)
                    .build();
            String reference = "";
            try(InputStream is = new FileInputStream(path.toFile())) {
                reference = storageProvider.store(StoreArg.builder()
                                .stream(is)
                                .path(getPath(tmpFileName, id))
                                .contentType(MediaTypeUtil.getMediaTypeForFileNameFromFileName(tmpFileName).toString())
                                .size(path.toFile().length())
                                .permission(permission)
                        .build());
            } catch (IOException e) {
                throw new StorageException("");
            }



            Media media = mediaRepository.findById(id).orElseThrow(() -> new StorageException(""));
            media.update(newpath.toFile().length(), MediaTypeUtil.getMediaTypeForFileNameFromFileName(tmpFileName).toString(), reference, true);
            if(media instanceof Image image){
                Dimension dimension = FFmpegUtils.getImageDimension(path);
                image.setDimension(dimension);
            } else if (media instanceof Video video){
                Dimension dimension = FFmpegUtils.getImageDimension(path);
                video.setDimension(dimension);
            }

            mediaRepository.save(media);
            newpath.toFile().delete();

        });
    }

    private String getPath(String fileName, ZID id){
        String newName = FileNameUtils.changeBaseName(fileName, id.toString());
        if (MediaTypeUtil.isAudioFile(fileName))
            return audioPath + "/" +newName;
        if (MediaTypeUtil.isImageFile(fileName))
            return imagePath + "/" + newName;
        if (MediaTypeUtil.isVideoFile(fileName))
            return videoPath + "/" + newName;

        throw new IllegalArgumentException();
    }
}