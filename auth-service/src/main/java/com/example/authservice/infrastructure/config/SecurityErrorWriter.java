package com.example.authservice.infrastructure.config;

import com.example.authservice.infrastructure.shared.ApiError;
import com.example.authservice.infrastructure.shared.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityErrorWriter {

    private final ObjectMapper objectMapper;

    public SecurityErrorWriter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Mono<Void> write(ServerWebExchange exchange, HttpStatus status, String code, String message) {
        try {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(status);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ApiResponse<Void> body = ApiResponse.error(new ApiError(code, message, exchange.getRequest().getPath().value(), null));
            byte[] bytes = objectMapper.writeValueAsBytes(body);
            DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(dataBuffer));
        } catch (Exception exception) {
            return exchange.getResponse().setComplete();
        }
    }
}
