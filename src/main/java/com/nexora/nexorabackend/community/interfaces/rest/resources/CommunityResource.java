package com.nexora.nexorabackend.community.interfaces.rest.resources;

public record CommunityResource(
        Long id,
        String name,
        String description,
        Long userId
) {
}
