package com.nexora.nexorabackend.social.interfaces.rest.resources;

public record LikeResource (
    Long id,
    Long userId,
    Long postId
){}