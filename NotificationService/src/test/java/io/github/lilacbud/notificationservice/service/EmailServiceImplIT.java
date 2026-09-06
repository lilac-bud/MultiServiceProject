package io.github.lilacbud.notificationservice.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;
import jakarta.mail.internet.MimeMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = EmailServiceImpl.class)
@EnableAutoConfiguration
public class EmailServiceImplIT {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
    
    @Autowired
    private EmailServiceImpl emailService;

    @Test
    public void givenEmailMessageDTO_whenSendingEmail_thenSendEmail() throws Exception {
        EmailMessageDTO dto = new EmailMessageDTO();
        dto.setEmail("test@gmail.com");
        dto.setSubject("Subject");
        dto.setMessage("Message");
        String expectedReceaver = "test@gmail.com";
        String expectedSubject = "Subject";
        String expectedText = "Message";
        
        emailService.sendEmail(dto);
        
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];
        assertEquals(expectedReceaver, receivedMessage.getAllRecipients()[0].toString());
        assertEquals(expectedSubject, receivedMessage.getSubject().trim());
        assertEquals(expectedText, receivedMessage.getContent().toString().trim());
    }
}
