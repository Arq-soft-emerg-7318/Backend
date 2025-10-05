package com.nexora.nexorabackend.subscription.domain.services;

import java.util.Optional;
import java.util.concurrent.Flow.Subscription;

import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;
import com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptionByUserId;

public interface SubscriptionQueryService 
{
    Optional<com.nexora.nexorabackend.subscription.domain.aggregates.Subscription> handle(GetSubscriptionByUserId command);

}

