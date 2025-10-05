package com.nexora.nexorabackend.subscription.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.nexora.nexorabackend.profiles.domain.model.aggregates.Profile;
import com.nexora.nexorabackend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;
import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;
import com.nexora.nexorabackend.subscription.domain.services.SubscriptionCommandService;
import com.nexora.nexorabackend.subscription.infrastructure.persistence.jpa.repositories.SubscriptionRepository;
@Service

public class SubscriptionCommandServiceImpl implements SubscriptionCommandService{

    private final SubscriptionRepository subscriptionRepository;
    private final ProfileRepository profileRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository, ProfileRepository profileRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Subscription> handle(CreateSubscriptionCommand command) {

    
        List<Profile> existingProfile = profileRepository.findByUserId(command.userId());
        if (existingProfile.isEmpty()) {
            throw new IllegalArgumentException("A profile does not exist for this user");
        }

        Date startDate = Date.valueOf(LocalDate.now());
        Date endDate = Date.valueOf(LocalDate.now().plusDays(30));
        command = new CreateSubscriptionCommand(
            command.planName(),
            command.price(),
            command.enable(),
            command.userId(),
            startDate,
            endDate
        );
        Subscription subscription = new Subscription(command);
        return Optional.of(subscriptionRepository.save(subscription));
        
    }
    
}
