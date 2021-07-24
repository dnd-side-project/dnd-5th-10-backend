package com.dnd10.iterview.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserServiceTest {
  //TODO:: 상용갔을때 deleteAll이 영향끼칠수 있지않을까
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @AfterEach
  void destroy() {
    userRepository.deleteAll();
  }

  @Test
  void getUserProfile() {
    //given
    String username = "choi";
    final String email = "test@gmail.com";
    User user = UserDto.builder().username(username)
        .password("")
        .provider(AuthProvider.google)
        .email(email)
        .providerId("11")
        .build()
        .toEntity();
    //when
    userRepository.save(user);

    //then
    final UserDto expected = new UserDto(user);
    final User sameUser = userRepository.findUserByEmail(email)
        .orElseThrow(IllegalArgumentException::new);
    final UserDto userDto = userService.getUserDetail(sameUser.getId());

    assertThat(userDto.getEmail()).isEqualTo(expected.getEmail());
    assertThat(userDto.getUsername()).isEqualTo(expected.getUsername());

  }

  @Test
  void updateUserName() {
    //given
    String username = "choi";
    String usernameExpected = "Kim";
    final String email = "test@gmail.com";
    User user = UserDto.builder().username(username)
        .password("")
        .provider(AuthProvider.google)
        .email(email)
        .providerId("googleId")
        .build()
        .toEntity();
    userRepository.save(user);
    //when
    final UserDto userDtoUpdated = UserDto.builder().email(email).username(usernameExpected)
        .password("").email(email).providerId("11").build();
    final User sameUser = userRepository.findUserByEmail(email)
        .orElseThrow(IllegalArgumentException::new);
    userService.update(sameUser.getId(), userDtoUpdated);
    //then
    assertThat(userService.getUserDetail(sameUser.getId()).getUsername()).isEqualTo(usernameExpected);
  }
}