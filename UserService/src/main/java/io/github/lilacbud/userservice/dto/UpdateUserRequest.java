package io.github.lilacbud.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Update user request")
public class UpdateUserRequest {
    @Schema(description = "New user name", example = "Alex")
    @Size(min = 1, max = 50, message = "User name length must be between 1 and 50 characters")
    private String name;

    @Schema(description = "New user email", example = "alex@gmail.com")
    @Size(min = 1, max = 50, message = "User email length must be between 1 and 50 characters")
    @Email(message = "User email must have a legal format")
    private String email;

    @Schema(description = "New user age", example = "18")
    @Positive(message = "User age must be greater than zero")
    private Integer age;
}
