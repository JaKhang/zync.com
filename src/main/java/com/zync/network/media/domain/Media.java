package com.zync.network.media.domain;

import com.zync.network.core.domain.ZID;import com.zync.network.core.domain.AggregateRoot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class Media extends AggregateRoot {
    ZID uploadBy;
    long size;
    String reference;
    boolean uploaded;
    String mediaType;

    public Media() {
        super();
    }

    public Media(ZID id, ZID uploadBy, long size, String reference, boolean uploaded, String mediaType) {
        super(id);
        this.uploadBy = uploadBy;
        this.size = size;
        this.reference = reference;
        this.uploaded = uploaded;
        this.mediaType = mediaType;
    }

    public void update(long size, String mediaType, String reference, boolean uploaded){
        this.size = size;
        this.mediaType = mediaType;
        this.reference = reference;
        this.uploaded = uploaded;
    }

}
