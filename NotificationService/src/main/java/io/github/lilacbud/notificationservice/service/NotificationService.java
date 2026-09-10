package io.github.lilacbud.notificationservice.service;

import io.github.lilacbud.commonmodels.UserMessage;

public interface NotificationService {
    void notifyUser(UserMessage message);
}
