package com.sadiker.mobisem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadiker.mobisem.controller.ToDoController;

import com.sadiker.mobisem.model.request.CreateToDoRequest;
import com.sadiker.mobisem.model.request.ToDoRequest;
import com.sadiker.mobisem.model.request.UserRequest;

import com.sadiker.mobisem.service.ToDoService;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @InjectMocks
    ToDoController toDoController;

    @Mock
    ToDoService toDoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }

    @Test
    void createToDo_Valid() throws Exception {

        ToDoRequest toDoRequest = ToDoRequest.builder().name("TODO 1").description("AÇIKLAMA 1")
                .day(20).month(5).year(2023).hour(14).minute(30).build();

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();

        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setToDoRequest(toDoRequest);
        createToDoRequest.setUserRequest(userRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        String toDoRequestJson = objectMapper.writeValueAsString(createToDoRequest);

        ResultActions result = mockMvc.perform(post("/createToDo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toDoRequestJson));
        result.andExpect(status().isOk());

    }

    @Test
    void createToDo_Invalid() throws Exception {
        // valid olmayan değerlerle oluşturduk. Yıl ve ay
        ToDoRequest toDoRequest = ToDoRequest.builder().name("TODO 1").description("AÇIKLAMA 1")
                .day(20).month(13).year(2100).hour(14).minute(30).build();

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();

        CreateToDoRequest createToDoRequest = new CreateToDoRequest();
        createToDoRequest.setToDoRequest(toDoRequest);
        createToDoRequest.setUserRequest(userRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        String toDoRequestJson = objectMapper.writeValueAsString(createToDoRequest);

        ResultActions result = mockMvc.perform(post("/createToDo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toDoRequestJson));
        result.andExpect(status().isBadRequest());

    }
}
