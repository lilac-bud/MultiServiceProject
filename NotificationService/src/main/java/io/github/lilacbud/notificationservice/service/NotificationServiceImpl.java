package io.github.lilacbud.notificationservice.service;

import io.github.lilacbud.commonmodels.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private static final String SUBJECT = "Notification";
    private static final String CREATED_MESSAGE = "Здравствуйте! Ваш аккаунт был успешно создан.";
    private static final String DELETED_MESSAGE = "Здравствуйте! Ваш аккаунт был удалён.";
    
    private final JavaMailSender mailSender;
    
    @Override
    @KafkaListener(topics = "user-events")
    public void notifyUser(UserMessage message) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(message.getUserEmail());
        simpleMessage.setSubject(SUBJECT);
        switch(message.getUserEvent()) {
            case USER_CREATED -> simpleMessage.setText(CREATED_MESSAGE);
            case USER_DELETED -> simpleMessage.setText(DELETED_MESSAGE);
        }
        mailSender.send(simpleMessage);
    }
}
