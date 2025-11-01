package com.nexora.nexorabackend.social.domain.services;

import java.util.Optional;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;

public interface PostCommandService {

    Optional<Post> handle(CreatePostCommand command);
    
} 