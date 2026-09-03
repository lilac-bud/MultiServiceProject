package io.github.lilacbud.userservice.mappers;

import io.github.lilacbud.userservice.dto.UserDTO;
import io.github.lilacbud.userservice.models.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapToUserEntity(UserDTO dto, User entity) {
        if (entity != null) {
            Optional.ofNullable(dto.getId()).ifPresent(entity::setId);
            Optional.ofNullable(dto.getName()).ifPresent(entity::setName);
            Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
            Optional.ofNullable(dto.getAge()).ifPresent(entity::setAge);
        }
        return entity;
    }
    
    public User mapToUserEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        return mapToUserEntity(dto, new User());
    }
    
    public UserDTO mapToUserDTO(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());
        return dto;
    }
}
