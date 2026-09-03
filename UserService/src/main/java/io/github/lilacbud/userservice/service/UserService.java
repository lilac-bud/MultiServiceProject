package io.github.lilacbud.userservice.service;

import io.github.lilacbud.userservice.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO findUserById(Long id);
    void deleteUserById(Long id);
    UserDTO saveUser(UserDTO dto);
    UserDTO updateUser(Long id, UserDTO dto);
    List<UserDTO> findAllUsers();
    void deleteAllUsers();
}
