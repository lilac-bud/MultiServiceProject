package io.github.lilacbud.projectgateway;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest
@ActiveProfiles("test")
@EnableWireMock({
    @ConfigureWireMock(
            port = 8081
    )
})
@AutoConfigureWebTestClient
public class ProjectGatewayApplicationIT {
    @Autowired
    private WebTestClient webClient;
    
    @Test
    public void givenThatServiceIsAvailable_whenMakingRequest_thenForwardToThatService() {
        stubFor(get("/test").willReturn(ok("success")));
        String expectedBody = "success";
        webClient.get().uri("/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo(expectedBody);
    }
    
    @Test
    public void givenThatServiceIsUnavailable_whenMakingRequest_thenForwardToFallback() {
        String expectedMessage = "Service is currently unavailable. Please, retry later.";
        webClient.get().uri("/unavble")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectHeader().valueEquals("Retry-After", "10")
                .expectBody(String.class).isEqualTo(expectedMessage);
    }
}
