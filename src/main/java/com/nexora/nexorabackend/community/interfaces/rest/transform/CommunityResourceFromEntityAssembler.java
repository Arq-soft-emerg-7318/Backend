package com.nexora.nexorabackend.community.interfaces.rest.transform;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.interfaces.rest.resources.CommunityResource;

public class CommunityResourceFromEntityAssembler {
    public static CommunityResource toResourceFromEntity (Community entity) {
        return new CommunityResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getUserId()
        );
    }
}
