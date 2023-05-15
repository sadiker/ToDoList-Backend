package com.sadiker.mobisem.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sadiker.mobisem.model.ToDo;
import com.sadiker.mobisem.model.User;
import com.sadiker.mobisem.model.request.UserRequest;
import com.sadiker.mobisem.model.response.IResponse;
import com.sadiker.mobisem.model.response.MyResponse;
import com.sadiker.mobisem.repository.UserRepository;

@Service
public class UserService {

    // @Autowired
    // PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    public IResponse register(UserRequest userRequest) {

        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());

        if (user.isPresent()) {

            return MyResponse.builder().message("Bu isimde kullanıcı mevcut,ismi değiştiriniz").build();
        }

        User saveUser = User.builder()
                .password(userRequest.getPassword())
                // default olarak USER rolünde kayıt var, istenilirse başka işlem(ADMIN vb.)
                // yapılır
                .role("NORMAL")
                // Her yeni User kaydında boş liste oluşturuyor.
                .todo(new ArrayList<ToDo>())
                .username(userRequest.getUsername())
                .build();

        userRepository.save(saveUser);

        return saveUser;

    }

}
