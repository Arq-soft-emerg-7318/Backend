package com.nexora.nexorabackend.social.interfaces.rest.transform;

import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreatePostResource;

public class CreatePostCommandFromResourceAssembler {

    public static CreatePostCommand toCommandFromResource (CreatePostResource resource, Long authorId){
        return new CreatePostCommand(
            resource.title(),
            authorId,
            resource.body(),
            resource.reactions(),
            resource.categoryId(),
            resource.fileId(),
            resource.communityId()
        );
    }
    
}
