package io.github.lilacbud.notificationservice.service;

import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;

public interface EmailService {
    void sendEmail(EmailMessageDTO dto);
}
