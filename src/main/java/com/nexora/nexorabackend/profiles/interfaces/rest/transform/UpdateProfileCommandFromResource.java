package com.nexora.nexorabackend.profiles.interfaces.rest.transform;

import com.nexora.nexorabackend.profiles.domain.model.commands.UpdateProfileCommand;
import com.nexora.nexorabackend.profiles.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResource {
    public static UpdateProfileCommand toCommandFromResource(UpdateProfileResource resource, Long userId) {
        return new UpdateProfileCommand(
            resource.firstName(),
            resource.lastName(),
            resource.email(),
            resource.direction(),
            resource.documentNumber(),
            resource.documentType(),
            resource.phone(),
            userId 
        );
    }
}