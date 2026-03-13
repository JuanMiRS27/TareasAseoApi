package com.example.taskservice.infrastructure.config;

import com.example.taskservice.infrastructure.adapters.security.JwtAuthenticationWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            JwtAuthenticationWebFilter jwtAuthenticationWebFilter,
            SecurityErrorWriter securityErrorWriter
    ) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((exchange, ex) ->
                                securityErrorWriter.write(exchange, HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "authentication required"))
                        .accessDeniedHandler((exchange, ex) ->
                                securityErrorWriter.write(exchange, HttpStatus.FORBIDDEN, "FORBIDDEN", "access denied"))
                )
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.POST, "/tasks").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.PUT, "/tasks/*/complete").hasAnyRole("ADMIN", "USER")
                        .pathMatchers(HttpMethod.PUT, "/tasks/*").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.DELETE, "/tasks/*").hasRole("ADMIN")
                        .pathMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("ADMIN", "USER")
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
