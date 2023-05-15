package com.sadiker.mobisem;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sadiker.mobisem.controller.UserController;
import com.sadiker.mobisem.model.User;
import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.IResponse;
import com.sadiker.mobisem.model.response.MyResponse;
import com.sadiker.mobisem.repository.UserRepository;
import com.sadiker.mobisem.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @ParameterizedTest(name = "Username:{0}")
    @ValueSource(strings = { "", "Test 1" })
    void register_ValidCheck(String given) throws Exception {
        String username = given;

        if (username.equals("")) {

            UserRequest userRequest = UserRequest.builder().username(username).password("12345").build();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(userRequest);

            ResultActions result = mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson));
            result.andExpect(status().isBadRequest());
        }

        if (username.equals("Test 1")) {

            UserRequest userRequest = UserRequest.builder().username(username).password("12345").build();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestJson = objectMapper.writeValueAsString(userRequest);

            ResultActions result = mockMvc.perform(post("/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson));
            result.andExpect(status().isOk());
        }

    }

    @Test
    void helloWorld() {

        String expectedString = "Merhaba Dünya";

        String actualString = userController.helloworld();

        assertEquals(expectedString, actualString);

    }

    @Test
    void register_UserNotExist() {

        UserRequest userRequest = UserRequest.builder().username("Deneme 1").password("12345").build();

        User expectedUser = User.builder().username(userRequest.getUsername()).password(userRequest.getPassword())
                .build();

        when(userService.register(any())).thenReturn(expectedUser);

        IResponse actualUser = userController.register(userRequest);

        assertAll(
                () -> assertEquals(expectedUser, actualUser));

    }

    @Test
    void register_UserExist() {

        UserRequest userRequest = UserRequest.builder().username("Deneme 1").password("12345").build();

        MyResponse expected = MyResponse.builder().message("Bu isimde kullanıcı mevcut,ismi değiştiriniz").build();

        when(userService.register(any())).thenReturn(expected);

        IResponse actual = userController.register(userRequest);

        assertAll(
                () -> assertEquals(expected, actual));

    }

}
