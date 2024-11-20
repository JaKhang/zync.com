package com.zync.network.media.application.events;

import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.mediator.Mediator;
import com.zync.network.media.application.commands.UpdateImageCommand;

import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.ImageRepository;
import com.zync.network.media.domain.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class MediaEventHandler {

    private final Mediator mediator;
    private final MediaRepository mediaRepository;
    private final ImageRepository imageRepository;

    @EventListener
    @Async
    @Transactional
    public void handler(ImageUploadedEvent event){
        Image image = imageRepository.findById(event.id()).orElseThrow(() -> new ResourceNotFoundException("Image", "Id", event.id().toLowerCase()));
        image.update(event.reference(), event.size(), true, event.dimension(), event.variants(), event.mediaType().toString());
        imageRepository.save(image);
    }


    @EventListener
    @Async
    @Transactional
    public void handler(MediaProcessingFailedEvent event){
        mediaRepository.deleteAll(event.ids());
    }
}
