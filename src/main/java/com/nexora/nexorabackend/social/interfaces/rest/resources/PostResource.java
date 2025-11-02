package com.nexora.nexorabackend.social.interfaces.rest.resources;


public record PostResource (
    Long id,
    String title,
    Integer authorId,
    String body,
    Integer reactions,
    Integer categoryId,
    Integer fileId,
    Integer communityId 
){}
