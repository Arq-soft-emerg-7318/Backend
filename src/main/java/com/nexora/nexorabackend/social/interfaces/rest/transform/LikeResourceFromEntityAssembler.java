package com.nexora.nexorabackend.social.interfaces.rest.transform;

import com.nexora.nexorabackend.social.interfaces.rest.resources.LikeResource;

public class LikeResourceFromEntityAssembler {
    
    public static LikeResource toResourceFromEntity (com.nexora.nexorabackend.social.domain.aggregates.Like like){
        return new LikeResource(
            like.getId(),
            like.getUserId(),
            like.getPostId()
        );
    }

}
