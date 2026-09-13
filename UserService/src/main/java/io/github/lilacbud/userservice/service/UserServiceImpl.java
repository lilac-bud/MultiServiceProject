package io.github.lilacbud.userservice.service;

import io.github.lilacbud.commonmodels.UserMessage;
import io.github.lilacbud.userservice.dto.CreateUserRequest;
import io.github.lilacbud.userservice.dto.UpdateUserRequest;
import io.github.lilacbud.userservice.dto.UserResponse;
import io.github.lilacbud.userservice.mappers.UserMapper;
import io.github.lilacbud.userservice.models.User;
import io.github.lilacbud.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String TOPIC = "user-events";
    private final UserRepository repository;
    private final UserMapper mapper;
    private final KafkaTemplate<String, UserMessage> kafkaTemplate;
    
    @Override
    @Transactional(readOnly = true)
    public UserResponse findUserById(Long id) {
        return mapper.mapToUserResponse(repository.findById(id).orElseThrow(() 
                -> new EntityNotFoundException("Failed to find user")));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        Optional<User> foundUser = repository.findById(id);
        repository.deleteById(id);
        foundUser.ifPresent(user -> {
            UserMessage message = new UserMessage();
            message.setUserEvent(UserMessage.UserEvent.USER_DELETED);
            message.setUserEmail(user.getEmail());
            kafkaTemplate.send(TOPIC, message);
        });
    }
    
    @Override
    @Transactional
    public UserResponse saveUser(CreateUserRequest dto) {
        User user = repository.save(mapper.mapToUserEntity(dto));
        UserMessage message = new UserMessage();
        message.setUserEvent(UserMessage.UserEvent.USER_CREATED);
        message.setUserEmail(user.getEmail());
        kafkaTemplate.send(TOPIC, message);
        return mapper.mapToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest dto) {
        User user = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Failed to find user"));
        return mapper.mapToUserResponse(repository.save(mapper.mapToUserEntity(dto, user)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        List<UserResponse> resultList = new ArrayList<>();
        repository.findAll().forEach(user -> resultList.add(mapper.mapToUserResponse(user)));
        return resultList;
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        Iterable<User> users = repository.findAll();
        repository.deleteAll();
        users.forEach(user -> {
            UserMessage message = new UserMessage();
            message.setUserEvent(UserMessage.UserEvent.USER_DELETED);
            message.setUserEmail(user.getEmail());
            kafkaTemplate.send(TOPIC, message);
        });
    } 
}
