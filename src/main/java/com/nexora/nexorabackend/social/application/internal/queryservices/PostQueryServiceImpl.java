package com.nexora.nexorabackend.social.application.internal.queryservices;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByCommunityId;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByUserIdQuery;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsQuery;
import com.nexora.nexorabackend.social.domain.queries.GetPostById;
import com.nexora.nexorabackend.social.domain.services.PostQueryService;
import com.nexora.nexorabackend.social.infrastructure.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostQueryServiceImpl implements PostQueryService {
    private final PostRepository postRepository;

    @Override
    public List<Post> handle(GetAllPostsQuery query) {
        return postRepository.findAll();
    }

    @Override
    public List<Post> handle(GetAllPostsByUserIdQuery query) {
        return postRepository.findAllByAuthorId(query.userId());
    }

    @Override
    public List<Post> handle(GetAllPostsByCommunityId query) {
        return postRepository.findAllByCommunityId(query.communityId());
    }

    @Override
    public Optional<Post> handle(GetPostById query) {
        return postRepository.findById(query.postId());
    }
}
