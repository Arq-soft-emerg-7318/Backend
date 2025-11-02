package com.nexora.nexorabackend.social.interfaces.rest.resources;

public record LikeResource (
    Long id,
    Integer userId,
    Integer postId 
){}