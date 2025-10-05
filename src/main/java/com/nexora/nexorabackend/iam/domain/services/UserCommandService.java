package com.nexora.nexorabackend.iam.domain.services;


import com.nexora.nexorabackend.iam.domain.model.aggregates.User;
import com.nexora.nexorabackend.iam.domain.model.commands.SignInCommand;
import com.nexora.nexorabackend.iam.domain.model.commands.SignUpCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(SignUpCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
}
