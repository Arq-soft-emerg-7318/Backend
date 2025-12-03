package com.nexora.nexorabackend.community.interfaces.rest.transform;

import com.nexora.nexorabackend.community.domain.model.commands.CreateCommunityCommand;
import com.nexora.nexorabackend.community.interfaces.rest.resources.CreateCommunityResource;

public class CreateCommunityCommandFromResourceAssembler {

    public static CreateCommunityCommand toCommandFromResource(CreateCommunityResource resource) {
        return new CreateCommunityCommand(
                resource.name(),
                resource.description(),
                resource.userId()
        );
    }

    public static CreateCommunityCommand toCommandFromResource(CreateCommunityResource resource, Long userId) {
        return new CreateCommunityCommand(
                resource.name(),
                resource.description(),
                userId
        );
    }
}
