package com.nexora.nexorabackend.social.domain.services;

import java.util.Optional;

import com.nexora.nexorabackend.social.domain.aggregates.Like;
import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;

public interface LikeCommandService {
    Optional<Like> handle(CreateLikeCommand command);
    
}
