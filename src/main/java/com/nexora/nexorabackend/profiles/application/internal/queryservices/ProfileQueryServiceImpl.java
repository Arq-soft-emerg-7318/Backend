package com.nexora.nexorabackend.profiles.application.internal.queryservices;


import com.nexora.nexorabackend.profiles.domain.model.aggregates.Profile;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetAllProfilesQuery;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetProfileByIdQuery;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetProfileByUserIdQuery;
import com.nexora.nexorabackend.profiles.domain.services.ProfileQueryService;
import com.nexora.nexorabackend.profiles.infrastructure.persistence.jpa.repositories.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final ProfileRepository profileRepository;

    public ProfileQueryServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.id());
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {
        return profileRepository.findByUserId(query.userId())
                .stream()
                .findFirst();
    }

    @Override
    public List<Profile> handle(GetAllProfilesQuery query) {
        return profileRepository.findAll();
    }
}
