package io.github.lilacbud.userservice.dto;

import io.github.lilacbud.userservice.validation.OnCreate;
import io.github.lilacbud.userservice.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    @Null(groups = {OnCreate.class, OnUpdate.class}, message = "No need to state User ID int the body")
    private Long id;
    @NotNull(groups = OnCreate.class, message = "User name must not be null")
    @Size(min = 1, max = 50, groups = {OnCreate.class, OnUpdate.class}, 
            message = "User name length must be between 1 and 50 characters")
    private String name;
    @NotNull(groups = OnCreate.class, message = "User email must not be null")
    @Size(min = 1, groups = {OnCreate.class, OnUpdate.class}, message = "User email must not be empty")
    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "User email must have a legal format")
    private String email;
    @Positive(groups = {OnCreate.class, OnUpdate.class}, message = "User age must be greater than zero")
    private Integer age;
}
