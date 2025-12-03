package com.nexora.nexorabackend.social.application.internal.queryservices;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.queries.GetAllLikesByPostIdQuery;
import com.nexora.nexorabackend.social.domain.services.LikeQueryService;
import com.nexora.nexorabackend.social.infrastructure.repositories.LikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeQueryServiceImpl implements LikeQueryService {
    private final LikeRepository likeRepository;

    @Override
    public long handle(GetAllLikesByPostIdQuery query) {
        return likeRepository.countLikesByPostId(query.postId());
    }
}
