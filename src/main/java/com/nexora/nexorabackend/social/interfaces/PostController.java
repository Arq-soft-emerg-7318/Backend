package com.nexora.nexorabackend.social.interfaces;

import java.nio.file.AccessDeniedException;
import java.io.IOException;
import java.util.List;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByUserIdQuery;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsQuery;
import com.nexora.nexorabackend.social.domain.services.PostQueryService;
import com.nexora.nexorabackend.social.domain.queries.GetAllPostsByCommunityId;
import org.springframework.security.core.Authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;
import com.nexora.nexorabackend.social.domain.services.PostCommandService;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreatePostResource;
import com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource;
// import assembler removed: we set authorId from authenticated user in controller
import com.nexora.nexorabackend.social.interfaces.rest.transform.PostResourceFromEntityAssembler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.stream.Collectors;
import com.nexora.nexorabackend.shared.Infrastructure.services.StorageService;

@RestController
@RequestMapping(value= "/api/v1/posts", produces =  MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;
    private final StorageService storageService;
    
    public PostController(PostCommandService postCommandService, PostQueryService postQueryService, StorageService storageService) {
        this.postQueryService = postQueryService;
        this.postCommandService = postCommandService;
        this.storageService = storageService;
        
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResource> createPost(@RequestPart("post") CreatePostResource resource,
                                                  @RequestPart(value = "file", required = false) MultipartFile file) throws AccessDeniedException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        Integer fileId = resource.fileId();
        if (file != null && !file.isEmpty()) {
            try {
                Integer savedFileId = storageService.store(file);
                if (savedFileId != null) fileId = savedFileId;
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        CreatePostCommand command = new CreatePostCommand(
            resource.title(),
            userId,
            resource.body(),
            resource.reactions(),
            resource.categoryId(),
            fileId,
            resource.communityId()
        );

        var post = postCommandService.handle(command);
        if (post.isEmpty()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        var postResource = PostResourceFromEntityAssembler.toResourceFromEntity(post.get());
        // make fileUrl absolute if present
        if (postResource.fileUrl() != null && postResource.fileId() != null) {
            String absolute = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/files/")
                    .path(String.valueOf(postResource.fileId()))
                    .toUriString();
            postResource = new PostResource(postResource.id(), postResource.title(), postResource.authorId(), postResource.body(), postResource.reactions(), postResource.categoryId(), postResource.fileId(), postResource.communityId(), absolute);
        }
        return new ResponseEntity<>(postResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource>> listAllPosts() {
        var posts = postQueryService.handle(new GetAllPostsQuery());
        var resources = posts.stream()
                .map(PostResourceFromEntityAssembler::toResourceFromEntity)
                .map(r -> {
                    if (r.fileUrl() != null && r.fileId() != null) {
                        String absolute = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/v1/files/")
                                .path(String.valueOf(r.fileId()))
                                .toUriString();
                        return new com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource(r.id(), r.title(), r.authorId(), r.body(), r.reactions(), r.categoryId(), r.fileId(), r.communityId(), absolute);
                    }
                    return r;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource>> listPostsByUser(@PathVariable Long userId) {
        var posts = postQueryService.handle(new GetAllPostsByUserIdQuery(userId));
        var resources = posts.stream()
                .map(PostResourceFromEntityAssembler::toResourceFromEntity)
                .map(r -> {
                    if (r.fileUrl() != null && r.fileId() != null) {
                        String absolute = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/v1/files/")
                                .path(String.valueOf(r.fileId()))
                                .toUriString();
                        return new com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource(r.id(), r.title(), r.authorId(), r.body(), r.reactions(), r.categoryId(), r.fileId(), r.communityId(), absolute);
                    }
                    return r;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource>> listPostsByCommunity(@PathVariable Long communityId) {
        var posts = postQueryService.handle(new GetAllPostsByCommunityId(communityId));
        var resources = posts.stream()
                .map(PostResourceFromEntityAssembler::toResourceFromEntity)
                .map(r -> {
                    if (r.fileUrl() != null && r.fileId() != null) {
                        String absolute = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/v1/files/")
                                .path(String.valueOf(r.fileId()))
                                .toUriString();
                        return new com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource(r.id(), r.title(), r.authorId(), r.body(), r.reactions(), r.categoryId(), r.fileId(), r.communityId(), absolute);
                    }
                    return r;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }
}
