package com.sadiker.mobisem;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.sadiker.mobisem.model.ToDo;
import com.sadiker.mobisem.model.User;
import com.sadiker.mobisem.repository.UserRepository;

@SpringBootApplication
@EnableCaching
public class MobisemApplication implements CommandLineRunner {

	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// return new BCryptPasswordEncoder();
	// }

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MobisemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findAll().size() == 0) {

			ToDo todo1 = ToDo.builder().name("İş görüşmesi").description("MOBİSEM")
					.line(0)
					.localDateTime(LocalDateTime.of(2023, 5, 22, 14, 30, 0)).build();

			ToDo todo2 = ToDo.builder().name("Case ").description("MOBİSEM")
					.line(1)

					.localDateTime(LocalDateTime.of(2023, 5, 8, 18, 30, 0)).build();

			ToDo todo3 = ToDo.builder().name("İş görüşmesi").description("MOBİSEM2")
					.line(0)
					.localDateTime(LocalDateTime.of(2023, 5, 22, 14, 30, 0)).build();

			ToDo todo4 = ToDo.builder().name("Case ").description("MOBİSEM2")
					.line(1)
					.localDateTime(LocalDateTime.of(2023, 5, 8, 18, 30, 0)).build();

			List<ToDo> todos1 = new ArrayList<ToDo>();
			todos1.add(todo1);
			todos1.add(todo2);

			List<ToDo> todos2 = new ArrayList<ToDo>();
			todos2.add(todo3);
			todos2.add(todo4);

			User user1 = new User();
			user1.setUsername("Kullanıcı 111111111");
			user1.setRole("NORMAL");
			user1.setPassword("12345");
			user1.setTodo(todos1);

			User user2 = new User();
			user2.setUsername("Kullanıcı 2");
			user2.setRole("ADMIN");
			user2.setPassword("12345");
			user2.setTodo(todos2);

			userRepository.save(user1);
			userRepository.save(user2);

		}
	}

}
