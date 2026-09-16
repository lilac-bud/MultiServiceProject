package io.github.lilacbud.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User response")
public class UserResponse {
    @Schema(description = "User id", example = "1")
    private Long id;

    @Schema(description = "User name", example = "Alex")
    private String name;

    @Schema(description = "User email", example = "alex@gmail.com")
    private String email;

    @Schema(description = "User age", example = "18")
    private Integer age;
}
