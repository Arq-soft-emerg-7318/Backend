package com.nexora.nexorabackend.profiles.interfaces.rest.transform;

import com.nexora.nexorabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.nexora.nexorabackend.profiles.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource, Long userId) {
        return new CreateProfileCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.direction(),
                resource.documentNumber(),
                resource.documentType()
        ,resource.phone(),
        userId);
    }
}