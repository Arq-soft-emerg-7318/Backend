package com.nexora.nexorabackend.community.domain.model.commands;

import java.util.Objects;

public record CreateCommunityCommand(String name, String description, Long userId) {
    public CreateCommunityCommand {
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(description, "Description cannot be null");
        Objects.requireNonNull(userId, "User ID cannot be null");
    }
}
