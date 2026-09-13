package io.github.lilacbud.userservice.controller;

import io.github.lilacbud.userservice.dto.CreateUserRequest;
import io.github.lilacbud.userservice.dto.UpdateUserRequest;
import io.github.lilacbud.userservice.dto.UserResponse;
import io.github.lilacbud.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
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
    
    @Tag(name = "find")
    @Operation(summary = "Get a list of all users")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Returned a list of all exising users"
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<UserResponse>> findAllUsers() {
        List<EntityModel<UserResponse>> userModels = service.findAllUsers().stream()
                .map(user -> EntityModel.of(
                        user, 
                        linkTo(methodOn(UserController.class).findUserById(user.getId())).withSelfRel()
                )).toList();
        return CollectionModel.of(userModels, linkTo(methodOn(UserController.class).findAllUsers()).withSelfRel());
    }
    
    @Tag(name = "find")
    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Returned an existing user with requested id"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Failed to find user",
                content = @Content
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public EntityModel<UserResponse> findUserById(@PathVariable Long id) {
        EntityModel<UserResponse> userModel = EntityModel.of(
                service.findUserById(id), 
                linkTo(methodOn(UserController.class).findUserById(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).findAllUsers()).withRel("all-users")
        );
        return userModel;
    }
    
    @Tag(name = "create")
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "201",
                description = "Successfully created user"
        )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public EntityModel<UserResponse> createUser(@Valid @RequestBody CreateUserRequest dto) {
        UserResponse user = service.saveUser(dto);
        EntityModel<UserResponse> userModel = EntityModel.of(
                user, 
                linkTo(methodOn(UserController.class).findUserById(user.getId())).withSelfRel()
        );
        return userModel;
    }
    
    @Tag(name = "update")
    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Successfully updated user"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Failed to find user",
                content = @Content
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public EntityModel<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest dto) {
        UserResponse user = service.updateUser(id, dto);
        EntityModel<UserResponse> userModel = EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).findUserById(user.getId())).withSelfRel()
        );
        return userModel;
    }
    
    @Tag(name = "delete")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public void deleteUserById(@PathVariable Long id) {
        service.deleteUserById(id);
    }
    
    @Tag(name = "delete")
    @Operation(summary = "Delete all users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    public void deleteAllUsers() {
        service.deleteAllUsers();
    }
}
