package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.PostResource;

public class PostResourceFromEntityAssembler {
    private PostResourceFromEntityAssembler() {}

    public static PostResource toResourceFromEntity(Post post) {
        return toResourceFromEntity(post, post.getLikes());
    }

    public static PostResource toResourceFromEntity(Post post, Integer likes) {
        return new PostResource(
                post.getId(),
                post.getCommunityId(),
                post.getUserId(),
                post.getContent(),
                post.getPoints(),
                likes,
                post.getImageUrl(),
                post.getCreatedAt()
        );
    }
}
