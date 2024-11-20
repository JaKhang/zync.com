package com.zync.network.media.domain;

import com.zync.network.core.domain.ZID;
import com.zync.network.media.domain.events.ImageUpdatedEvent;
import com.zync.network.media.domain.events.VariantCreatedEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Image extends Media{
    @Getter
    Dimension dimension;
    @ElementCollection
    List<Variant> variants = new ArrayList<>();

    public void crateVariant(String reference, int width, int height){
        Variant variant = new Variant(reference, new Dimension(width, height));
        registerEvents(new VariantCreatedEvent(id, reference, width, height));
    }

    protected Image() {
    }

    public Image(ZID id, ZID uploadBy, long size, String reference, boolean uploaded, String mediaType, Dimension dimension, List<Variant> variants) {
        super(id, uploadBy, size, reference, uploaded, mediaType);
        this.dimension = dimension;
        this.variants = variants;
    }

    public void update(String reference, long size, boolean uploaded, Dimension dimension, List<Variant> variants, String mediaType) {
        this.reference = reference;
        this.size = size;
        this.mediaType = mediaType;
        this.variants.addAll(variants);
        this.dimension = dimension;
        this.uploaded = uploaded;
        registerEvents(new ImageUpdatedEvent());
    }

}
