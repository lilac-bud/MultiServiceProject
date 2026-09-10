package io.github.lilacbud.notificationservice.service;

import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    
    @Override
    public void sendEmail(EmailMessageDTO dto) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(dto.getEmail());
        simpleMessage.setSubject(dto.getSubject());
        simpleMessage.setText(dto.getMessage());
        mailSender.send(simpleMessage);
    }   
}
