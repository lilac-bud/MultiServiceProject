package io.github.lilacbud.userservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserMessage {
    private final UserEvent userEvent;
    private final String userEmail;
    
    public static enum UserEvent {
        USER_CREATED,
        USER_DELETED
    }
}
