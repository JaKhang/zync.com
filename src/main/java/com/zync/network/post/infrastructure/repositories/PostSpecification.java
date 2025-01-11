package com.zync.network.post.infrastructure.repositories;

import com.zync.network.core.domain.ZID;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostType;
import com.zync.network.post.domain.Visibility;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> byParentAndType(ZID parentId, PostType type) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get("type"), type),
                builder.equal(root.get("parentId"), parentId),
                builder.equal(root.get("deleted"), false)
        );
    }

    public static Specification<Post> byThreadId(ZID threadId){
        return (root, query, builder) -> builder.and(
                builder.equal(root.get("deleted"), false),
                builder.equal(root.get("threadId"), threadId)
        );
    }

    public static Specification<Post> checkVisibility(boolean isFollowing, boolean isSelf, boolean deleted, ZID selfId) {
        return (root, query, builder) -> {
            if (isSelf) return builder.equal(root.get("deleted"), deleted);
            var any = builder.equal(root.get("visibility"), Visibility.ANY);
            var followers = isFollowing ? builder.equal(root.get("visibility"), Visibility.FOLLOWING) : builder.disjunction();
            var mention = builder.and(
                    builder.isMember(selfId, root.get("mentionIds")),
                    builder.equal(root.get("visibility"), Visibility.MENTIONED)
            );
            var spec = builder.or(
                    any,
                    followers,
                    mention
            );
            return builder.and(
                    spec,
                    builder.equal(root.get("deleted"), deleted)
            );
        };
    }

}
