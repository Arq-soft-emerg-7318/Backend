package com.nexora.nexorabackend.community.domain.services;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.domain.model.queries.GetAllCommunitiesQuery;
import com.nexora.nexorabackend.community.domain.model.queries.GetCommunityByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CommunityQueryService {
    List<Community> handle(GetAllCommunitiesQuery query);
    Optional<Community> handle(GetCommunityByIdQuery query);
}
