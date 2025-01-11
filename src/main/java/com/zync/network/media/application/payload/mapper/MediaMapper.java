package com.zync.network.media.application.payload.mapper;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.application.payload.MediaPayLoad;
import com.zync.network.media.application.payload.MediaType;
import com.zync.network.media.domain.Dimension;
import com.zync.network.media.domain.Image;
import com.zync.network.media.domain.Media;
import com.zync.network.media.domain.Video;
import com.zync.network.media.infrastructure.factories.URLFactory;
import com.zync.network.media.infrastructure.utils.FFmpegUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MediaMapper {

    URLFactory urlFactory;
   public List<MediaPayLoad> map(Collection<? extends Media> collection){
        return collection.stream().map(this::map).toList();
    }

    public MediaPayLoad map(Media media){
       int width = 0;
       int height =0 ;
        if(media instanceof Image image){
            width = image.getDimension().width();
            height = image.getDimension().height();
        } else if (media instanceof Video video){
            width = video.getDimension().width();
            height = video.getDimension().height();
        }

        if (media.getReference() == null || media.getReference().isBlank())
            return defaultMedia(media.getId());


        return MediaPayLoad.builder()
                .id(media.getId())
                .type(MediaType.fromMime(media.getMediaType()).toString())
                .url(urlFactory.generate(media))
                .width(width)
                .height(height)
                .build();
    }

    private MediaPayLoad defaultMedia(ZID id) {
        return MediaPayLoad.builder()
                .id(id)
                .type(MediaType.IMAGE.toString())
                .url(urlFactory.generate(null))
                .width(600)
                .height(600)
                .build();
    }


}
