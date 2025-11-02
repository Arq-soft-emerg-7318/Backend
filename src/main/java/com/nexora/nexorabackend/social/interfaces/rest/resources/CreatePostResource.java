package com.nexora.nexorabackend.social.interfaces.rest.resources;


public record CreatePostResource (
    String title,
    Integer authorId,
    String body,
    Integer reactions,
    Integer categoryId,
    Integer fileId,
    Integer communityId 
){}
