package com.nexora.nexorabackend.iam.interfaces.rest.transform;


import com.nexora.nexorabackend.iam.domain.model.commands.SignInCommand;
import com.nexora.nexorabackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
