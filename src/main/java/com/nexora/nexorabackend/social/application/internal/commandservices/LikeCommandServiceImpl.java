package com.nexora.nexorabackend.social.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nexora.nexorabackend.social.domain.aggregates.Like;
import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;
import com.nexora.nexorabackend.social.domain.services.LikeCommandService;
import com.nexora.nexorabackend.social.infrastructure.repositories.LikeRepository;

@Service
public class LikeCommandServiceImpl implements LikeCommandService {

    private final LikeRepository likeRepository;
    

    public LikeCommandServiceImpl(LikeRepository likeRepository) {
            this.likeRepository = likeRepository;
    }


    @Override
    public Optional<Like> handle(CreateLikeCommand command) {
        // create entity from command and persist
        Like like = new Like(command);
        Like saved = likeRepository.save(like);
        return Optional.ofNullable(saved);
    }
    
}
