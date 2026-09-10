package io.github.lilacbud.commonmodels;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class UserMessage {
    private UserEvent userEvent;
    private String userEmail;
    
    @Getter
    @RequiredArgsConstructor
    public static enum UserEvent {
        USER_CREATED("Здравствуйте! Ваш аккаунт был успешно создан."),
        USER_DELETED("Здравствуйте! Ваш аккаунт был удалён.");
        
        private final String text;
    }
}
