package com.nexora.nexorabackend.social.application.internal.commandservices;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;
import com.nexora.nexorabackend.social.domain.services.PostCommandService;
import com.nexora.nexorabackend.social.infrastructure.repositories.PostRepository;

@Service

public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    

    public PostCommandServiceImpl(PostRepository postRepository) {
            this.postRepository = postRepository;
    }


    @Override
    public Optional<Post> handle(CreatePostCommand command) {
        
       CreatePostCommand commad = new CreatePostCommand(
        command.title(),
        command.authorId(),
        command.body(),
        command.reactions(),
        command.categoryId(),
        command.fileId(),
        command.communityId());
        Post post = new Post(commad);
        return Optional.of(postRepository.save(post));

    }   


}