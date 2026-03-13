package com.example.authservice.infrastructure.config;

import com.example.authservice.domain.entities.Role;
import com.example.authservice.domain.entities.User;
import com.example.authservice.domain.ports.PasswordEncoderPort;
import com.example.authservice.domain.ports.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public ApplicationRunner adminBootstrapRunner(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            @Value("${security.bootstrap.admin.username:admin}") String adminUsername,
            @Value("${security.bootstrap.admin.password:admin123}") String adminPassword
    ) {
        return args -> userRepositoryPort.findByUsername(adminUsername)
                .switchIfEmpty(userRepositoryPort.save(
                        new User(null, adminUsername, passwordEncoderPort.encode(adminPassword), Role.ADMIN)
                ))
                .subscribe();
    }
}
