package com.sadiker.mobisem.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sadiker.mobisem.model.ToDo;
import com.sadiker.mobisem.model.User;
import com.sadiker.mobisem.model.request.CreateToDoRequest;
import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.IResponse;
import com.sadiker.mobisem.model.response.MyResponse;
import com.sadiker.mobisem.repository.UserRepository;

@Service
public class ToDoService {

    @Autowired
    UserRepository userRepository;

    @CacheEvict(value = "todos",key="#createToDoRequest.userRequest.username")
    public MyResponse createToDo(CreateToDoRequest createToDoRequest) {

        LocalDate localDate;
        LocalTime localTime;
        User user;
        ToDo todo;

        try {

            localDate = LocalDate.of(createToDoRequest.getToDoRequest().getYear(),
                    createToDoRequest.getToDoRequest().getMonth(),
                    createToDoRequest.getToDoRequest().getDay());
            localTime = LocalTime.of(createToDoRequest.getToDoRequest().getHour(),
                    createToDoRequest.getToDoRequest().getMinute());

            Optional<User> optUser = userRepository.findByUsername(createToDoRequest.getUserRequest().getUsername());
            if (optUser.isPresent()
                    && optUser.get().getPassword().equals(createToDoRequest.getUserRequest().getPassword())) {
                user = optUser.get();

                todo = ToDo.builder()
                        .name(createToDoRequest.getToDoRequest().getName())
                        .description(createToDoRequest.getToDoRequest().getDescription())
                        .localDateTime(LocalDateTime.of(localDate, localTime))
                        .build();

                user.getTodo().add(todo);

                for (int i = 0; i < user.getTodo().size(); i++) {
                    user.getTodo().get(i).setLine(i + 1);
                }

                userRepository.save(user);
                // todos(createToDoRequest.getUserRequest());

                return MyResponse.builder()
                        .message(user.getUsername() + " adlı kullanıcının planı kaydedildi.")
                        .data(todo)
                        .build();
            }

            return MyResponse.builder()
                    .message("Kullanıcı kayıtlı değil veya şifre hatalı")
                    .build();

        } catch (Exception e) {

            return MyResponse.builder().message(e.getMessage()).data("İşlemde  hata oluştu(createToDo)").build();
        }

    }

    @Caching(evict = { @CacheEvict(value  = "todos",key = "#userRequest.username")})
    public IResponse deleteToDoByName(String name, UserRequest userRequest) {

        User user;

        try {

            Optional<User> optUser = userRepository.findByUsername(userRequest.getUsername());
            if (optUser.isPresent() && optUser.get().getPassword().equals(userRequest.getPassword())) {
                user = optUser.get();

                // TODO name'i eşit olmayanlardan yeni bir liste oluşturup set eder
                List<ToDo> newTodos = user.getTodo().stream()
                        .filter(todo -> !(todo.getName().toLowerCase().equals(name.toLowerCase()))).toList();

                for (int i = 0; i < user.getTodo().size(); i++) {
                    user.getTodo().get(i).setLine(i + 1);
                }
                user.setTodo(newTodos);
                userRepository.save(user);
                return MyResponse.builder()
                        .message(user.getUsername() + " adlı kullanıcının " + name + " adlı tüm planları silindi.")
                        .build();

            }

            return MyResponse.builder()
                    .message("Kullanıcı kayıtlı değil veya şifre hatalı")
                    .build();

        } catch (Exception e) {
            return MyResponse.builder().message(e.getMessage()).data("İşlemde hata oluştu(deleteToDoName)").build();
        }

    }

    @Cacheable(value = "todos",key = "#userRequest.username")
    public IResponse todos(UserRequest userRequest) {

        User user;

        try {
            Thread.sleep(2000);
            Optional<User> optUser = userRepository.findByUsername(userRequest.getUsername());
            if (optUser.isPresent() && optUser.get().getPassword().equals(userRequest.getPassword())) {
                user = optUser.get();
                return MyResponse.builder()
                        .message(user.getUsername() + " adlı kullanıcının tüm planları geldi.")
                        .data(user.getTodo())
                        .build();
            }

            return MyResponse.builder()
                    .message("Kullanıcı kayıtlı değil veya şifre hatalı")
                    .build();

        } catch (Exception e) {
            return MyResponse.builder().message(e.getMessage()).data("İşlemde hata oluştu(todos)").build();

        }

    }

  
    @Caching(evict = { @CacheEvict(value  = "todos",key = "#userRequest.username")})
    public IResponse deleteToDoByLine(int line, UserRequest userRequest) {
        User user;
        try {

            Optional<User> optUser = userRepository.findByUsername(userRequest.getUsername());
            if (optUser.isPresent() && optUser.get().getPassword().equals(userRequest.getPassword())) {
                user = optUser.get();
                user.getTodo().remove(line - 1);
                for (int i = 0; i < user.getTodo().size(); i++) {
                    user.getTodo().get(i).setLine(i + 1);
                }
                userRepository.save(user);
                return MyResponse.builder()
                        .message(user.getUsername() + " adlı kullanıcının " + line + " numaralı planı silindi.")
                        .build();
            }
            return MyResponse.builder()
                    .message("Kullanıcı kayıtlı değil veya şifre hatalı")
                    .build();

        } catch (Exception e) {
            return MyResponse.builder().message(e.getMessage()).data("İşlemde hata oluştu(deleteToDoLine)").build();
        }

    }
}
