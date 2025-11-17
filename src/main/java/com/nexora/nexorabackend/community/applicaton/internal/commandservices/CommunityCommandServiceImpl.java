package com.nexora.nexorabackend.community.applicaton.internal.commandservices;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.domain.model.commands.CreateCommunityCommand;
import com.nexora.nexorabackend.community.domain.services.CommunityCommandService;
import com.nexora.nexorabackend.community.infrastructure.repositories.CommunityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommunityCommandServiceImpl implements CommunityCommandService {
    private final CommunityRepository communityRepository;

    public CommunityCommandServiceImpl(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    @Override
    public Optional<Community> handle(CreateCommunityCommand command) {
        // Create Community directly from the incoming command (includes sector)
        Community community = new Community(command);
        return Optional.of(communityRepository.save(community));

    }
}
