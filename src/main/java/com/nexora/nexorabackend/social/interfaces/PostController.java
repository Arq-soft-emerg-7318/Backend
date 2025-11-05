package com.nexora.nexorabackend.social.interfaces;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByUserIdQuery;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsQuery;
import com.nexora.nexorabackend.social.domain.services.PostQueryService;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;
import com.nexora.nexorabackend.social.domain.services.PostCommandService;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreatePostResource;
import com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource;
import com.nexora.nexorabackend.social.interfaces.rest.transform.CreatePostCommandFromResourceAssembler;
import com.nexora.nexorabackend.social.interfaces.rest.transform.PostResourceFromEntityAssembler;

@RestController
@RequestMapping(value= "/api/v1/posts", produces =  MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    public PostController(PostCommandService postCommandService, PostQueryService postQueryService) {
        this.postQueryService = postQueryService;
        this.postCommandService = postCommandService;
    }

    @PostMapping
    public ResponseEntity<PostResource> createPost(@RequestBody CreatePostResource resource) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        CreatePostCommand oldCommand = CreatePostCommandFromResourceAssembler.toCommandFromResource(resource);

        CreatePostCommand command = new CreatePostCommand(
            oldCommand.title(),
            userId,
            oldCommand.body(),
            oldCommand.reactions(),
            oldCommand.categoryId(),
            oldCommand.fileId(),
            oldCommand.communityId()
        );

        var post = postCommandService.handle(command);
        if (post.isEmpty()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
        return new ResponseEntity<>(postResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> listAllPosts() {
        var posts = postQueryService.handle(new GetAllPostsQuery());
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> listPostsByUser(@PathVariable Long userId) {
        var posts = postQueryService.handle(new GetAllPostsByUserIdQuery(userId));
        return ResponseEntity.ok(posts);
    }
}
