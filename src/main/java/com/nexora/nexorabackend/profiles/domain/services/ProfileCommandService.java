package com.nexora.nexorabackend.profiles.domain.services;


import  com.nexora.nexorabackend.profiles.domain.model.aggregates.Profile;
import  com.nexora.nexorabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.nexora.nexorabackend.profiles.domain.model.commands.UpdateProfileCommand;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);

    void handle(Long userId, CreateProfileCommand command);
}
