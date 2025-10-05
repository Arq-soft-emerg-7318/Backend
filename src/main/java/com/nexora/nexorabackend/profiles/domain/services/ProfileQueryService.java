package com.nexora.nexorabackend.profiles.domain.services;


import com.nexora.nexorabackend.profiles.domain.model.aggregates.Profile;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetAllProfilesQuery;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetProfileByIdQuery;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetProfileByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByIdQuery query);
    List<Profile> handle(GetAllProfilesQuery query);
    Optional<Profile> handle(GetProfileByUserIdQuery query);
    
}
