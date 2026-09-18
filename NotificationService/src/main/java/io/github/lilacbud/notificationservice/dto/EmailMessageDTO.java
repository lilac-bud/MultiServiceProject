package io.github.lilacbud.notificationservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailMessageDTO {
    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must have a legal format")
    private String email;
    
    @NotNull(message = "Email subject must not be null")
    private String subject;
    
    @NotNull(message = "Email message must not be null")
    private String message;
}
