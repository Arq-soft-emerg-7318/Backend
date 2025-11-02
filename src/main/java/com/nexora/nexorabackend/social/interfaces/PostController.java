package com.nexora.nexorabackend.social.interfaces;

import java.nio.file.AccessDeniedException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;
import com.nexora.nexorabackend.social.domain.services.PostCommandService;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreatePostResource;
import com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource;
import com.nexora.nexorabackend.social.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import com.nexora.nexorabackend.social.interfaces.rest.transform.PostResourceFromEntityAssembler;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value= "/api/v1/posts", produces =  MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostCommandService postCommandService;

    public PostController(PostCommandService postCommandService) {
        this.postCommandService = postCommandService;
    }


    @PostMapping
    public ResponseEntity<PostResource> createPost(@RequestBody CreatePostResource resource) throws AccessDeniedException {
        CreatePostCommand command = CreatePostCommandFromResourceAssembler.toCommandFromResource(resource);
        var post = postCommandService.handle(command);
        if (post.isEmpty()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
        return new ResponseEntity<>(postResource, HttpStatus.CREATED);

    }

    
}
