package io.github.lilacbud.notificationservice.controller;

import io.github.lilacbud.notificationservice.dto.EmailMessageDTO;
import io.github.lilacbud.notificationservice.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-message")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService service;
    
    @Operation(summary = "Send an email message")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "The email message was sent"
        )
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void sendEmail(@Valid @RequestBody EmailMessageDTO dto) {
        service.sendEmail(dto);
    }
}
