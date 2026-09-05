package io.github.lilacbud.notificationservice.dto;

import lombok.Data;

@Data
public class EmailMessageDTO {
    private String email;
    private String subject;
    private String message;
}
