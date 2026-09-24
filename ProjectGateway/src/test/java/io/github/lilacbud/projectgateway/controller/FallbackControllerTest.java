package io.github.lilacbud.projectgateway.controller;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(FallbackController.class)
public class FallbackControllerTest { 
    @Autowired
    private WebTestClient webClient;
    
    @ParameterizedTest
    @MethodSource("provideHttpMethods")
    public void givenProvidedHttpMethods_whenGettingFallbackMessage_thenReturnMessage(HttpMethod method) {
        String expectedMessage = "Service is currently unavailable. Please, retry later.";
        webClient.method(method).uri("/fallback/message")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.SERVICE_UNAVAILABLE)
                .expectHeader().valueEquals("Retry-After", "10")
                .expectBody(String.class).isEqualTo(expectedMessage);
    }
    
    @SuppressWarnings("unused")
    private static Stream<HttpMethod> provideHttpMethods() {
        return Stream.of(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PATCH,
                HttpMethod.DELETE
        );
    }
}
