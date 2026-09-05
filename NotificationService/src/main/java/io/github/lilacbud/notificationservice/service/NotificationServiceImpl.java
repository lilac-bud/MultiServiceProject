package io.github.lilacbud.notificationservice.service;

import io.github.lilacbud.commonmodels.UserMessage;
import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final String SUBJECT = "Уведомление";
    private static final String CREATED_MESSAGE = "Здравствуйте! Ваш аккаунт был успешно создан.";
    private static final String DELETED_MESSAGE = "Здравствуйте! Ваш аккаунт был удалён.";
    
    private final EmailService emailService;
     
    @Override
    @KafkaListener(topics = "user-events")
    public void notifyUser(UserMessage message) {
        EmailMessageDTO dto = new EmailMessageDTO();
        dto.setEmail(message.getUserEmail());
        dto.setSubject(SUBJECT);
        switch(message.getUserEvent()) {
            case USER_CREATED -> dto.setMessage(CREATED_MESSAGE);
            case USER_DELETED -> dto.setMessage(DELETED_MESSAGE);
        }
        emailService.sendEmail(dto);
    }
}
