package io.github.lilacbud.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "User response")
public class UserResponse {
    @Schema(description = "User id", example = "1")
    @NotNull
    private Long id;

    @Schema(description = "User name", example = "Alex")
    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @Schema(description = "User email", example = "alex@gmail.com")
    @NotNull
    @Size(min = 1, max = 50)
    @Email
    private String email;

    @Schema(description = "User age", example = "18")
    @Positive
    private Integer age;
}
