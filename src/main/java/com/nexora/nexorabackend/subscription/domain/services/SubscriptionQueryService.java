package com.nexora.nexorabackend.subscription.domain.services;

import java.util.List;
import java.util.Optional;

import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;
import com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptionByUserId;
import com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptions;

public interface SubscriptionQueryService 
{
    Optional<Subscription> handle(GetSubscriptionByUserId query);
    List<Subscription> handle(GetSubscriptions query);

}

