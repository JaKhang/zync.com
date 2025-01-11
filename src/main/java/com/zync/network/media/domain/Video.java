package com.zync.network.media.domain;

import com.zync.network.core.domain.ZID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends Media{
    Long duration;
    Integer resolution;
    @Setter
    @Getter
    Dimension dimension = new Dimension(0,0);

    public Video(ZID id, ZID uploadBy, long size, String reference, boolean uploaded, String mediaType, Long duration, Integer resolution) {
        super(id, uploadBy, size, reference, uploaded, mediaType);
        this.duration = duration;
        this.resolution = resolution;
    }
}
