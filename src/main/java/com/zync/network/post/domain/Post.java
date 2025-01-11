package com.zync.network.post.domain;

import com.zync.network.core.domain.AggregateRoot;
import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.BadRequestException;
import com.zync.network.post.domain.events.*;
import com.zync.network.user.domain.events.PostCreatedEvent;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AggregateRoot {
    @ElementCollection
    List<ZID> mediaIds;
    String content;
    @ElementCollection
    Set<ZID> mentionIds = new HashSet<>();
    PostType type;
    ZID authorId;
    ZID parentId;
    ZID threadId;
    @Setter
    Visibility visibility;
    boolean pin = false;
    @ElementCollection
    Set<ZID> likes = new HashSet<>();


    public Post(List<ZID> mediaIds, String content, Set<ZID> mentionIds, PostType type, ZID authorId, ZID parentId, ZID threadId, Visibility visibility) {
        this.id = ZID.fast();
        this.mediaIds = mediaIds;
        this.content = content;
        this.mentionIds = mentionIds;
        this.type = type;
        this.authorId = authorId;
        this.parentId = parentId;
        this.threadId = threadId;
        this.visibility = visibility;
        registerEvents(new PostCreatedEvent(this.id, mentionIds, authorId, type.toString(), parentId, threadId, LocalDateTime.now()));

    }

    public Post repost(ZID userId, String content, List<ZID> mediaIds, Set<ZID> mentionIds, Visibility visibility) {
        Post post = new Post(mediaIds, content, mentionIds, PostType.REPOST, userId, this.id, ZID.fast(), visibility);
        registerEvents(new PostRePostedEvent(userId, authorId, post.id, mentionIds, LocalDateTime.now()));
        return post;
    }


    public void like(ZID userId) {
        if (isLiked(userId)) throw new BadRequestException();
        likes.add(userId);
        registerEvents(new LikedPostEvent(this.id, userId, authorId, LocalDateTime.now()));

    }

    public void unlike(ZID userId) {
        if (!isLiked(userId)) throw new BadRequestException();
        likes.remove(userId);
        registerEvents(new UnlikedPostEvent(this.id, userId));
    }

    public void update(String content, Set<ZID> mentionIds, List<ZID> mediaIds) {
        this.content = content;
        this.mediaIds = mediaIds;
        this.mentionIds = mentionIds;
        registerEvents(new PostUpdatedEvent(content, mediaIds, mentionIds));
    }

    public Post reply(ZID userId, String content, Set<ZID> mentionIds, List<ZID> mediaIds, Visibility visibility) {
        Post post = new Post(
                mediaIds,
                content,
                mentionIds,
                PostType.REPLY,
                userId,
                this.id,
                threadId,
                visibility

        );
        registerEvents(new PostRepliedEvent(authorId, authorId, post.id, mentionIds, LocalDateTime.now()));
        return post;
    }


    public boolean isLiked(ZID userId) {
        return Hibernate.contains(likes, userId);
    }

    public int countLikes() {
        return likes.size();
    }
}
