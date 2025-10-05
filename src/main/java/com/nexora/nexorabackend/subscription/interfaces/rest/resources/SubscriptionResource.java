package com.nexora.nexorabackend.subscription.interfaces.rest.resources;

public record SubscriptionResource (
        Long id,
        String planName,
        Float price,
        Boolean enable,
        Long userId
){}