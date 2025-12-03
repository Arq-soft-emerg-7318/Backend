package com.nexora.nexorabackend.community.domain.services;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.domain.model.commands.CreateCommunityCommand;

import java.util.Optional;

public interface CommunityCommandService {

    Optional<Community> handle(CreateCommunityCommand command);

}
