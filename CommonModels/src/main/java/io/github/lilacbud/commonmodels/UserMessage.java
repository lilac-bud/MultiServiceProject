package io.github.lilacbud.commonmodels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMessage {
    private UserEvent userEvent;
    private String userEmail;
    
    public static enum UserEvent {
        USER_CREATED,
        USER_DELETED
    }
}
