package io.github.lilacbud.notificationservice.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import io.github.lilacbud.commonmodels.UserMessage;
import jakarta.mail.internet.MimeMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@EmbeddedKafka(topics = "user-events", partitions = 1, bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@DirtiesContext
public class NotificationServiceImplIT {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withDisabledAuthentication());
    
    @Autowired
    KafkaTemplate<String, UserMessage> kafkaTemplate;

    @Test
    public void givenUserEventAndEmail_whenSendingUserMessage_thenSendNotificationEmail() throws Exception {
        UserMessage message = new UserMessage();
        message.setUserEvent(UserMessage.UserEvent.USER_CREATED);
        message.setUserEmail("test@gmail.com");
        String expectedReceiver = "test@gmail.com";
        String expectedSubject = "Уведомление";
        String expectedText = "Здравствуйте! Ваш аккаунт был успешно создан.";
        
        kafkaTemplate.send("user-events", message);

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];
        assertEquals(expectedReceiver, receivedMessage.getAllRecipients()[0].toString());
        assertEquals(expectedSubject, receivedMessage.getSubject().trim());
        assertEquals(expectedText, receivedMessage.getContent().toString().trim());
    }   
}
