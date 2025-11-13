package com.nexora.nexorabackend.community.applicaton.internal.queryservices;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.domain.model.queries.GetAllCommunitiesQuery;
import com.nexora.nexorabackend.community.domain.model.queries.GetCommunityByIdQuery;
import com.nexora.nexorabackend.community.domain.services.CommunityQueryService;
import com.nexora.nexorabackend.community.infrastructure.repositories.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommunityQueryServiceImpl implements CommunityQueryService {
    private final CommunityRepository communityRepository;

    @Override
    public List<Community> handle(GetAllCommunitiesQuery query) { return  communityRepository.findAll(); }

    @Override
    public Optional<Community> handle(GetCommunityByIdQuery query) { return communityRepository.findCommunityById(query.communityId()); }
}
