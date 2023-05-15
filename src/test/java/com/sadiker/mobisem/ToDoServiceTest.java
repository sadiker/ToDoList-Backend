package com.sadiker.mobisem;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sadiker.mobisem.model.ToDo;
import com.sadiker.mobisem.model.User;
import com.sadiker.mobisem.model.request.CreateToDoRequest;
import com.sadiker.mobisem.model.request.ToDoRequest;
import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.MyResponse;
import com.sadiker.mobisem.repository.UserRepository;
import com.sadiker.mobisem.service.ToDoService;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {

    @InjectMocks
    ToDoService todoService;
    @Mock
    UserRepository userRepository;

    @ParameterizedTest(name = "Line:{0}")
    @ValueSource(ints = { 1, 2, 3, 10, })
    void deleteToDoByLine_UserExist(int givenline) {

        ToDo todo1 = ToDo.builder().name("Deneme 1").line(1).build();
        ToDo todo2 = ToDo.builder().name("Deneme 2").line(2).build();
        List<ToDo> todos = new ArrayList<ToDo>();

        int line = givenline;

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();
        User user = User.builder().username(userRequest.getUsername()).todo(todos).password(userRequest.getPassword())
                .build();
        user.getTodo().add(todo2);
        user.getTodo().add(todo1);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        if (line == 1 || line == 2) {

            String expected = user.getUsername() + " adlı kullanıcının " + line + " numaralı planı silindi.";

            MyResponse myResponse = (MyResponse) todoService.deleteToDoByLine(line, userRequest);

            Assertions.assertEquals(expected, myResponse.getMessage());

        }
        if (line > 2) {
            String expected = "İşlemde hata oluştu(deleteToDoLine)";

            MyResponse myResponse = (MyResponse) todoService.deleteToDoByLine(line, userRequest);

            Assertions.assertEquals(expected, myResponse.getData());
        }

    }

    @Test
    void deleteToDoByLine_UserNotExist() {

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();
        User user = User.builder().username(userRequest.getUsername()).password(userRequest.getPassword()).build();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        String expected = "Kullanıcı kayıtlı değil veya şifre hatalı";

        MyResponse myResponse = (MyResponse) todoService.deleteToDoByLine(1, userRequest);

        Assertions.assertEquals(expected, myResponse.getMessage());
    }

    @Test
    void deleteToDoByLine_PasswordWrong() {

        ToDo todo1 = ToDo.builder().name("Deneme 1").line(1).build();
        List<ToDo> todos = new ArrayList<ToDo>();
        todos.add(todo1);

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("ABCDE").build();
        User user = User.builder().username(userRequest.getUsername()).password("12345").todo(todos).build();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        String expected = "Kullanıcı kayıtlı değil veya şifre hatalı";

        MyResponse myResponse = (MyResponse) todoService.deleteToDoByLine(1, userRequest);

        Assertions.assertEquals(expected, myResponse.getMessage());
    }

    @ParameterizedTest(name = "Line:{0}")
    @ValueSource(strings = { "MOBİSEM" })
    void exception(String givenline) {

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();

        String line = givenline;

        Executable executable = () -> todoService.deleteToDoByLine(Integer.parseInt(line), userRequest);

        Assertions.assertThrows(NumberFormatException.class, executable);

        Executable executable2 = () -> todoService.deleteToDoByLine((Integer) null, userRequest);
        Assertions.assertThrows(NullPointerException.class, executable2);
    }

    @Test
    void todos_UserExist() {

        ToDo todo1 = ToDo.builder().name("Deneme 1").line(1).build();
        ToDo todo2 = ToDo.builder().name("Deneme 2").line(2).build();
        List<ToDo> todos = new ArrayList<ToDo>();

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();
        User user = User.builder().username(userRequest.getUsername()).todo(todos).password(userRequest.getPassword())
                .build();
        user.getTodo().add(todo2);
        user.getTodo().add(todo1);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<ToDo> todoList = userRepository.findByUsername(user.getUsername()).get().getTodo();

        assertAll(
                () -> assertEquals(user.getTodo(), todoList),
                () -> assertEquals(user.getTodo().get(0).getName(), todoList.get(0).getName()));
    }

    @Test
    void todos_UserNotExist() {

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        MyResponse actualString = (MyResponse) todoService.todos(userRequest);
        assertAll(
                () -> assertEquals("Kullanıcı kayıtlı değil veya şifre hatalı", actualString.getMessage()));
    }

    @ParameterizedTest(name = "Exist:{0}")
    @ValueSource(booleans = { false, true })
    void createToDo_UserExistOrNotExist(boolean givenBoolean) {

        boolean exist = givenBoolean;

        ToDoRequest toDoRequest = ToDoRequest.builder().name("TODO 1").description("AÇIKLAMA 1")
                .day(22).month(5).year(2023).hour(14).minute(30).build();

        ToDo expectedToDo = ToDo.builder().name("TODO 1").description("AÇIKLAMA 1")
                .localDateTime(LocalDateTime.of(2023, 5, 22, 14, 30, 0)).line(1).build();

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();

        User expectedUser = User.builder().username("Test 1").password("12345").todo(new ArrayList<ToDo>()).build();
        if (exist == true) {
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expectedUser));

            MyResponse actual = (MyResponse) todoService
                    .createToDo(CreateToDoRequest.builder().toDoRequest(toDoRequest).userRequest(userRequest).build());

            Assertions.assertEquals(expectedToDo, actual.getData());
            Assertions.assertEquals("Test 1 adlı kullanıcının planı kaydedildi.", actual.getMessage());
        }
        if (exist == false) {
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
            MyResponse actual = (MyResponse) todoService
                    .createToDo(CreateToDoRequest.builder().toDoRequest(toDoRequest).userRequest(userRequest).build());
            Assertions.assertEquals(null, actual.getData());
            Assertions.assertEquals("Kullanıcı kayıtlı değil veya şifre hatalı", actual.getMessage());
        }

    }

    @Test
    void createToDo_Exception() {

        UserRequest userRequest = UserRequest.builder().username("Test 1").password("12345").build();
        ToDoRequest toDoRequest = ToDoRequest.builder().name("TODO 1").description("AÇIKLAMA 1")
                .day(50).month(5).year(2030).hour(14).minute(30).build();

        MyResponse actual = (MyResponse) todoService

                .createToDo(CreateToDoRequest.builder().toDoRequest(toDoRequest).userRequest(userRequest).build());

        Assertions.assertEquals("İşlemde  hata oluştu(createToDo)", actual.getData());
    }
}
