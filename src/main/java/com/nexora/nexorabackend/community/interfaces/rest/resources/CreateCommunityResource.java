package com.nexora.nexorabackend.community.interfaces.rest.resources;

public record CreateCommunityResource(
        String name,
        String description,
        Long userId
) {
}
