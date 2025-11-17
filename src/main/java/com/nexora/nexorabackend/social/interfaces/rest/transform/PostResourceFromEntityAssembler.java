package com.nexora.nexorabackend.social.interfaces.rest.transform;

import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.interfaces.rest.resources.PostResource;

public class PostResourceFromEntityAssembler {

    public static PostResource toResourceFromEntity (Post entity){
        String fileUrl = null;
        if (entity.getFileId() != null) {
            fileUrl = "/api/v1/files/" + entity.getFileId();
        }
        return new PostResource(
            entity.getId(),
            entity.getTitle(),
            entity.getAuthorId(),
            entity.getBody(),
            entity.getReactions(),
            entity.getCategoryId(),
            entity.getFileId(),
            entity.getCommunityId(),
            fileUrl
        );
    }
    
}
