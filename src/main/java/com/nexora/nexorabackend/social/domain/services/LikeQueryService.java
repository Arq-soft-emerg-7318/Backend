package com.nexora.nexorabackend.social.domain.services;

import com.nexora.nexorabackend.social.domain.queries.GetAllLikesByPostIdQuery;

public interface LikeQueryService {
    long handle(GetAllLikesByPostIdQuery query);
}
