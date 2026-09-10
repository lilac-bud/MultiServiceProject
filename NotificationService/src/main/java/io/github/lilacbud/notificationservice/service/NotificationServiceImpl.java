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
    
    private final EmailService emailService;
     
    @Override
    @KafkaListener(topics = "user-events")
    public void notifyUser(UserMessage message) {
        EmailMessageDTO dto = new EmailMessageDTO();
        dto.setEmail(message.getUserEmail());
        dto.setSubject(SUBJECT);
        dto.setMessage(message.getUserEvent().getText());
        emailService.sendEmail(dto);
    }
}
