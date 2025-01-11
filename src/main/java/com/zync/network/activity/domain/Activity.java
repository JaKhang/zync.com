package com.zync.network.activity.domain;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.ZID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "activities")
@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity extends AggregateRoot {
    ActivityType type;
    ZID recipientId;
    ZID actorId;
    ZID postId;
    @ColumnDefault("false")
    boolean seen = false;
    LocalDateTime at;

    public Activity(ActivityType type, ZID actorId, ZID recipientId,ZID postId, LocalDateTime at) {
        super(ZID.fast());
        this.type = type;
        this.actorId = actorId;
        this.postId = postId;
        this.recipientId = recipientId;
        this.at = at;
        registerEvents(new ActivityCreatedEvent(id, type.toString(), recipientId, actorId, postId, at));
    }



    public boolean is(ActivityType activityType){
        return Objects.equals(activityType, type);
    }

    public void seen() {
        seen = true;
    }
}
