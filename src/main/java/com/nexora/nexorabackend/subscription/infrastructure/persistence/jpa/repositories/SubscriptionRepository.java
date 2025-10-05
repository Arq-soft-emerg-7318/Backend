package com.nexora.nexorabackend.subscription.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Optional<Subscription> findByUserId(Long userId);
    
}
