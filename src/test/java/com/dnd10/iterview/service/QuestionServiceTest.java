package com.dnd10.iterview.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Pageable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class QuestionServiceTest {

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private QuestionService questionService;

  private String user1 = "test@gmail.com";
  private String user2 = "test2@gmail.com";

  @AfterEach
  public void setUp() {
    questionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("문제 조회")
  void getQuestion(){
    signUp(user1);

    Question question = makeQuestion(user1);
    QuestionResponseDto dto = questionService.getQuestion(question.getId());

    assertThat(dto.getId()).isEqualTo(question.getId());
  }

  @Test
  @DisplayName("문제 생성")
  void addQuestion(){
    User user = signUp(user1);

    Principal principal = new UserPrincipal(user.getEmail());
    QuestionRequestDto dto = QuestionRequestDto.builder()
        .content("test question")
        .bookmarkCount(10L)
        .tags(new ArrayList<>()) // todo: 태그 나중에
        .build();

    QuestionResponseDto responseDto = questionService.addQuestion(principal, dto);
    assertThat(questionRepository.existsById(responseDto.getId())).isTrue();
  }

  @Test
  @DisplayName("모든 문제 리스트 조회")
  void getAllQuestion(){
    signUp(user1);

    makeQuestion(user1);
    makeQuestion(user1);

    assertThat(questionService.getAllQuestions(Pageable.ofSize(3)).size()).isEqualTo(2);
  }

  @Test
  @DisplayName("문제 검색")
  void getSearchQuestion(){
    signUp(user1);

    makeQuestion(user1);
    makeQuestion(user1);

    assertThat(questionService.getSearchQuestions(new ArrayList<>(), "테스트", Pageable.ofSize(3)).size())
        .isEqualTo(2);
    assertThat(questionService.getSearchQuestions(new ArrayList<>(), "틀렸당", Pageable.ofSize(3)).size())
        .isEqualTo(0);
  }

  @Test
  @DisplayName("내가 만든 문제 조회")
  void getMyAllQuestion(){
    User userOne = signUp(user1);

    makeQuestion(user1);
    makeQuestion(user1); // user 1

    String username = "leetest";
    final String email = user2;
    User userTwo = UserDto.builder().username(username)
        .provider(AuthProvider.google)
        .email(email)
        .providerId("11")
        .build()
        .toEntity();
    userRepository.save(userTwo);

    makeQuestion(user2); // user 2

    Principal principal1 = new UserPrincipal(userOne.getEmail());
    Principal principal2 = new UserPrincipal(userTwo.getEmail());

    assertThat(questionService.getMyAllQuestions(principal1, Pageable.ofSize(5)).size()).isEqualTo(2);
    assertThat(questionService.getMyAllQuestions(principal2, Pageable.ofSize(5)).size()).isEqualTo(1);
  }

  private User signUp(String userEmail){
    String username = "lee";
    final String email = userEmail;
    User user = UserDto.builder().username(username)
        .provider(AuthProvider.google)
        .email(email)
        .providerId("11")
        .build()
        .toEntity();

    return userRepository.save(user);
  }

  private Question makeQuestion(String userEmail){
    final User user = userRepository.findUserByEmail(userEmail)
        .orElseThrow(IllegalArgumentException::new);
    Question question = Question.builder()
        .content("문제 테스트 입니다. 문제 테스트 입니다.")
        .userManager(user)
        .bookmarkCount(0L)
        .questionTagList(new ArrayList<>())
        .build();

    return questionRepository.save(question);
  }

}
