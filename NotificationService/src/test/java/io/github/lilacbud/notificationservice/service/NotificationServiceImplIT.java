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

@SpringBootTest(properties = {
    "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
    "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
    "spring.kafka.producer.acks=0",
    "spring.kafka.consumer.group-id=testgroup",
    "spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
    "spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
    "spring.kafka.consumer.properties.spring.json.trusted.packages=io.github.lilacbud.*",
    "spring.kafka.consumer.properties.spring.json.value.default.type=io.github.lilacbud.commonmodels.UserMessage",
    "spring.kafka.consumer.auto-offset-reset=latest"
})
@EmbeddedKafka(topics = "user-events", bootstrapServersProperty = "spring.kafka.bootstrap-servers")
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
        String expectedReceaver = "test@gmail.com";
        String expectedSubject = "Уведомление";
        String expectedText = "Здравствуйте! Ваш аккаунт был успешно создан.";
        
        kafkaTemplate.send("user-events", message);

        greenMail.waitForIncomingEmail(1);
        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        MimeMessage receivedMessage = receivedMessages[0];
        assertEquals(expectedReceaver, receivedMessage.getAllRecipients()[0].toString());
        assertEquals(expectedSubject, receivedMessage.getSubject().trim());
        assertEquals(expectedText, receivedMessage.getContent().toString().trim());
    }   
}
