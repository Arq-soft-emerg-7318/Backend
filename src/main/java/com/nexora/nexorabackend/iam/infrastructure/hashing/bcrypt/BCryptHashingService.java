package com.nexora.nexorabackend.iam.infrastructure.hashing.bcrypt;


import com.nexora.nexorabackend.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {

}
