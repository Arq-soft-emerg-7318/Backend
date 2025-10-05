package com.nexora.nexorabackend.subscription.application.internal.queryservices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;
import com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptionByUserId;
import com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptions;
import com.nexora.nexorabackend.subscription.domain.services.SubscriptionQueryService;
import com.nexora.nexorabackend.subscription.infrastructure.persistence.jpa.repositories.SubscriptionRepository;

@Service
public class SubscripcionQueryServiceImpl implements SubscriptionQueryService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscripcionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByUserId query) {
        return subscriptionRepository.findByUserId(query.userId());
    
    }

    @Override
    public List<Subscription> handle(GetSubscriptions query) {
        return subscriptionRepository.findAll();
    }
}
