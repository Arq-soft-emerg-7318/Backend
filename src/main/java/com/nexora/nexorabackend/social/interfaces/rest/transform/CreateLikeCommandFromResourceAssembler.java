package com.nexora.nexorabackend.social.interfaces.rest.transform;

import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;
import com.nexora.nexorabackend.social.interfaces.rest.resources.CreateLikeResource;

public class CreateLikeCommandFromResourceAssembler {

    public static CreateLikeCommand toCommandFromResource (CreateLikeResource resource){
        return new CreateLikeCommand(
            resource.userId(),
            resource.postId()
        );
    }
    
}
