package io.github.lilacbud.userservice.service;

import io.github.lilacbud.userservice.dto.CreateUserRequest;
import io.github.lilacbud.userservice.dto.UpdateUserRequest;
import io.github.lilacbud.userservice.dto.UserResponse;
import java.util.List;

public interface UserService {
    UserResponse findUserById(Long id);
    void deleteUserById(Long id);
    UserResponse saveUser(CreateUserRequest dto);
    UserResponse updateUser(Long id, UpdateUserRequest dto);
    List<UserResponse> findAllUsers();
    void deleteAllUsers();
}
