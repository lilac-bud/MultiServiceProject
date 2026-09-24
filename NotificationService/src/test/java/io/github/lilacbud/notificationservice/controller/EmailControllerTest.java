package io.github.lilacbud.notificationservice.controller;

import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;
import io.github.lilacbud.notificationservice.service.EmailService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailController.class)
public class EmailControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private EmailService service;

    @Test
    public void givenThatEmailMessageIsValid_whenSendingEmail_thenReturnStatusOk() throws Exception {
        EmailMessageDTO dto = new EmailMessageDTO();
        dto.setEmail("test@gmail.com");
        dto.setSubject("");
        dto.setMessage("");
        mvc.perform(post("/email-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"test@gmail.com\", \"subject\": \"\", \"message\": \"\"}"))
                .andExpect(status().isOk());
        verify(service).sendEmail(dto);
    }
    
    @Test
    public void givenThatEmailMessageIsInvalid_whenSendingEmail_thenReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/email-message")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
