package io.github.lilacbud.userservice.mappers;

import io.github.lilacbud.userservice.dto.CreateUserRequest;
import io.github.lilacbud.userservice.dto.UpdateUserRequest;
import io.github.lilacbud.userservice.dto.UserResponse;
import io.github.lilacbud.userservice.models.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToUserEntity(CreateUserRequest dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setAge(dto.getAge());
        return entity;
    }
    
    public User mapToUserEntity(UpdateUserRequest dto, User entity) {
        if (dto != null && entity != null) {
            Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
            Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
            Optional.ofNullable(dto.getAge()).ifPresent(entity::setAge);
        }
        return entity;
    }
    
    public UserResponse mapToUserResponse(User entity) {
        if (entity == null) {
            return null;
        }
        UserResponse dto = new UserResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());
        return dto;
    }
}
