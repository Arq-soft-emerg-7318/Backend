package com.nexora.nexorabackend.subscription.domain.services;

import java.util.Optional;

import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;
import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;

public interface SubscriptionCommandService {

    Optional<Subscription>handle(CreateSubscriptionCommand command);

    
    
}
