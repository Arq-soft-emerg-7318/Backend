package com.nexora.nexorabackend.social.interfaces;

import java.nio.file.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.nexorabackend.social.domain.aggregates.Like;
import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;
import com.nexora.nexorabackend.social.domain.services.LikeCommandService;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreateLikeResource;
import com.nexora.nexorabackend.social.interfaces.rest.resources.LikeResource;
import com.nexora.nexorabackend.social.interfaces.rest.transform.LikeResourceFromEntityAssembler;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value= "/api/v1/likes", produces =  MediaType.APPLICATION_JSON_VALUE)
public class LikeController {


        private final LikeCommandService likeCommand;

        public LikeController(LikeCommandService likeCommand) {
            this.likeCommand = likeCommand;
        }

        @PostMapping
    public ResponseEntity<LikeResource> createLike(@RequestBody CreateLikeResource resource) throws AccessDeniedException{

            // obtain authenticated user id from security context when possible
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = null;
            if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl) {
                UserDetailsImpl ud = (UserDetailsImpl) auth.getPrincipal();
                Long uid = ud.getUserDetailsId();
                if (uid != null) userId = uid.intValue();
            }

            // fallback to value provided in the request body (if any)
            if (userId == null) userId = resource.userId();

            if (userId == null) {
                // user id missing: unauthorized / bad request depending on your policy
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            CreateLikeCommand command = new CreateLikeCommand(userId, resource.postId());
            var like =  likeCommand.handle(command);

            if (like.isEmpty()) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            var likeResource = LikeResourceFromEntityAssembler.toResourceFromEntity(like.get());

            return new ResponseEntity<>(likeResource, HttpStatus.CREATED);
        }

}
