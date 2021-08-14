package com.dnd10.iterview.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class QuestionServiceTest {

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private QuestionService questionService;

  @AfterEach
  public void setUp() {
    questionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("문제 조회")
  void getQuestion(){
    signUp();

    Question question = makeQuestion();
    QuestionResponseDto dto = questionService.getQuestion(question.getId());

    assertThat(dto.getId()).isEqualTo(question.getId());
  }

  @Test
  @DisplayName("모든 문제 리스트 조회")
  void getAllQuestion(){

  }

  @Test
  @DisplayName("문제 검색")
  void getSearchQuestion(){

  }

  private void signUp(){
    String username = "lee";
    final String email = "test@gmail.com";
    User user = UserDto.builder().username(username)
        .provider(AuthProvider.google)
        .email(email)
        .providerId("11")
        .build()
        .toEntity();

    userRepository.save(user);
  }

  private Question makeQuestion(){
    final User user = userRepository.findUserByEmail("test@gmail.com")
        .orElseThrow(IllegalArgumentException::new);
    Question question = Question.builder()
        .content("문제 테스트 입니다. 문제 테스트 입니다.")
        .userManager(user)
        .bookmarkCount(0L)
        .build();

    return questionRepository.save(question);
  }

}
