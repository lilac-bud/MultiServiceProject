package io.github.lilacbud.userservice.controller;

import io.github.lilacbud.userservice.dto.CreateUserRequest;
import io.github.lilacbud.userservice.dto.UpdateUserRequest;
import io.github.lilacbud.userservice.dto.UserResponse;
import io.github.lilacbud.userservice.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private UserService service;
    
    private final UserResponse user1;
    private final UserResponse user2;
    
    public UserControllerTest() {
        user1 = new UserResponse();
        user1.setId(1L);
        user1.setName("Alex");
        user1.setEmail("alex@alex.com");
        user1.setAge(18);
        user2 = new UserResponse();
        user2.setId(2L);
        user2.setName("Rose");
        user2.setEmail("rose@rose.com");
        user2.setAge(25);
    }

    @Test
    public void givenThatUsersExist_whenFindingAllUsers_thenReturnAllUsers() throws Exception {
        when(service.findAllUsers()).thenReturn(List.of(user1, user2));
        mvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void givenThatUserExists_whenFindingUserById_thenReturnUser() throws Exception {
        when(service.findUserById(1L)).thenReturn(user1);
        mvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@alex.com"))
                .andExpect(jsonPath("$.age").value(18));
    }
    
    @Test
    public void givenThatUserDoesNotExist_whenFindingUserById_thenReturnStatusNotFound() throws Exception {
        when(service.findUserById(1L)).thenThrow(EntityNotFoundException.class);
        mvc.perform(get("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenThatUserIsValid_whenCreatingUser_thenReturnCreatedUser() throws Exception {
        CreateUserRequest dto = new CreateUserRequest();
        dto.setName("Alex");
        dto.setEmail("alex@alex.com");
        dto.setAge(18);
        when(service.saveUser(dto)).thenReturn(user1);
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Alex\", \"email\": \"alex@alex.com\", \"age\": 18}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@alex.com"))
                .andExpect(jsonPath("$.age").value(18));
    }
    
    @Test
    public void givenThatUserIsInvalid_whenCreatingUser_thenReturnStatusBadRequest() throws Exception {
        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenThatUserExistsAndUpdateIsValid_whenUpdatingUser_thenReturnUpdatedUser() throws Exception {
        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setEmail("alex@alex.com");
        when(service.updateUser(1L, dto)).thenReturn(user1);
        mvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"alex@alex.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alex"))
                .andExpect(jsonPath("$.email").value("alex@alex.com"))
                .andExpect(jsonPath("$.age").value(18));
    }
    
    @Test
    public void givenThatUserDoesNotExist_whenUpdatingUser_thenReturnStatusNotFound() throws Exception {
        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setEmail("alex@alex.com");
        when(service.updateUser(1L, dto)).thenThrow(EntityNotFoundException.class);
        mvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"alex@alex.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void givenThatUpdateIsInvalid_whenUpdatingUser_thenReturnStatusBadRequest() throws Exception {
        mvc.perform(patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"age\": -5}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
            
    @Test
    public void givenAnyId_whenDeletingUser_thenReturnStatusNoContent() throws Exception {
        mvc.perform(delete("/users/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenAnyCondition_whenDeletingAllUsers_thenReturnStatusNoContent() throws Exception {
        mvc.perform(delete("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
