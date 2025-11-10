package com.nexora.nexorabackend.social.domain.services;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByCommunityId;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByUserIdQuery;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsQuery;
import com.nexora.nexorabackend.social.domain.queries.GetPostById;

import java.util.List;
import java.util.Optional;

public interface PostQueryService {
    List<Post> handle(GetAllPostsQuery query);
    List<Post> handle(GetAllPostsByUserIdQuery query);
    List<Post> handle(GetAllPostsByCommunityId query);
    Optional<Post> handle(GetPostById query);
}
