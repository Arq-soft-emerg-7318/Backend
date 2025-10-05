package com.nexora.nexorabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NexoraBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NexoraBackendApplication.class, args);
    }

}
                                                                                               