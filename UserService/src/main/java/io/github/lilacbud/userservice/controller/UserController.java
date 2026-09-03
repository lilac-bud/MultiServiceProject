package io.github.lilacbud.userservice.controller;

import io.github.lilacbud.userservice.dto.UserDTO;
import io.github.lilacbud.userservice.service.UserService;
import io.github.lilacbud.userservice.validation.OnCreate;
import io.github.lilacbud.userservice.validation.OnUpdate;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<UserDTO> findAllUsers() {
        return service.findAllUsers();
    }
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public UserDTO findUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }
    
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Validated(OnCreate.class)
    public UserDTO createUser(@Valid @RequestBody UserDTO dto) {
        return service.saveUser(dto);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Validated(OnUpdate.class)
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
        return service.updateUser(id, dto);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteUserById(@PathVariable Long id) {
        service.deleteUserById(id);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public void deleteAllUsers() {
        service.deleteAllUsers();
    }
}
