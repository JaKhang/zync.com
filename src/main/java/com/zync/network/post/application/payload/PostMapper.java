package com.zync.network.post.application.payload;

import com.zync.network.core.domain.ZID;
import com.zync.network.core.exceptions.ResourceNotFoundException;
import com.zync.network.core.factories.TimePayloadFactory;
import com.zync.network.media.application.clients.MediaClient;
import com.zync.network.post.domain.Post;
import com.zync.network.post.domain.PostType;
import com.zync.network.post.infrastructure.repositories.PostJPARepository;
import com.zync.network.user.application.clients.UserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zync.network.post.infrastructure.repositories.PostSpecification.byParentAndType;
import static com.zync.network.post.infrastructure.repositories.PostSpecification.byThreadId;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PostMapper {
    MediaClient mediaClient;
    UserClient userClient;
    TimePayloadFactory timePayloadFactory;
    PostJPARepository repository;

    public PostPayLoad map(Post post, ZID selfId) {

        Specification<Post> countRepost = byParentAndType(post.getId(), PostType.REPOST);

        PostPayLoad parent = null;

        if (post.getParentId()!= null)
            parent = mapParent(post.getParentId(), selfId);

        return PostPayLoad.builder()
                .liked(post.isLiked(selfId))
                .likes(post.countLikes())
                .author(userClient.findById(post.getAuthorId(), selfId))
                .type(post.getType().toString())
                .content(post.getContent())
                .id(post.getId())
                .media(mediaClient.findMedia(post.getMediaIds()))
                .time(timePayloadFactory.create(post.getCreatedAt()))
                .createdAt(post.getCreatedAt())
                .reposts(repository.count(countRepost))
                .replies(countAllDescendants(post))
                .parent(parent)
                .build();
    }

    private PostPayLoad mapParent(ZID parentId, ZID selfId) {
        Post post = repository.findById(parentId).orElseThrow(() -> new ResourceNotFoundException("Posts", "Id", parentId));
        Specification<Post> countRepost = byParentAndType(post.getId(), PostType.REPOST);

        return PostPayLoad.builder()
                .liked(post.isLiked(selfId))
                .likes(post.countLikes())
                .author(userClient.findById(post.getAuthorId(), selfId))
                .type(post.getType().toString())
                .content(post.getContent())
                .id(post.getId())
                .media(mediaClient.findMedia(post.getMediaIds()))
                .time(timePayloadFactory.create(post.getCreatedAt()))
                .createdAt(post.getCreatedAt())
                .reposts(repository.count(countRepost))
                .replies(countAllDescendants(post))
                .parent(null)
                .build();

    }

    public List<PostPayLoad> map(List<Post> posts, ZID selfId) {
        return posts.stream().map((p) -> map(p, selfId)).toList();
    }

    private int countAllDescendants(Post post){
       if (post.getType() == PostType.REPLY)
           return repository.count(byParentAndType(post.getId(), PostType.REPLY));

       return repository.count(byThreadId(post.getThreadId())) - 1;
    }
}
