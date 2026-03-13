package com.example.authservice.infrastructure.adapters.persistence;

import com.example.authservice.domain.entities.Role;
import com.example.authservice.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public User toDomain(UserR2dbcEntity entity) {
        return new User(entity.getId(), entity.getUsername(), entity.getPassword(), Role.valueOf(entity.getRole()));
    }

    public UserR2dbcEntity toEntity(User user) {
        return new UserR2dbcEntity(user.id(), user.username(), user.password(), user.role().name());
    }
}
