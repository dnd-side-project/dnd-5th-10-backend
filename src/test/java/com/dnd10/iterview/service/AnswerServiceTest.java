package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerRequestDto;
import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.LikeAnswerRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AnswerServiceTest {

  AnswerService answerService;
  AnswerRepository answerRepository;
  UserRepository userRepository;
  QuestionRepository questionRepository;
  LikeAnswerRepository likeAnswerRepository;

  @Autowired
  public AnswerServiceTest(AnswerService answerService,
      AnswerRepository answerRepository, UserRepository userRepository,
      QuestionRepository questionRepository,
      LikeAnswerRepository likeAnswerRepository) {
    this.answerService = answerService;
    this.answerRepository = answerRepository;
    this.userRepository = userRepository;
    this.questionRepository = questionRepository;
    this.likeAnswerRepository = likeAnswerRepository;
  }

  @AfterEach
  void removeAll() {
    likeAnswerRepository.deleteAll();
    answerRepository.deleteAll();
    questionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void answer_create_and_find_should_work() {

    final User testUser = User.builder()
        .username("testUser")
        .email("testANSWER@gmail.com")
        .provider(AuthProvider.google)
        .providerId("123")
        .build();
    final User savedUser = userRepository.save(testUser);

    final Question question = Question.builder()
        .content("question")
        .bookmarkCount(0L)
        .create_date(LocalDate.now())
        .userManager(savedUser)
        .build();
    final Question savedQuestion = questionRepository.save(question);

    final Answer answer = Answer.builder()
        .content("test")
        .liked(0L)
        .user(testUser)
        .question(savedQuestion)
        .build();
    final Answer savedAnswer = answerRepository.save(answer);

    final AnswerRequestDto answerRequestDto = AnswerRequestDto.builder()
        .content(answer.getContent())
        .liked(answer.getLiked())
        .questionId(savedQuestion.getId())
        .userId(savedUser.getId())
        .build();
    final AnswerResponseDto answerSaved = answerService.createAnswer(answerRequestDto);

    final Page<AnswerResponseDto> allAnswers = answerService.getAllAnswersByQuestion(savedQuestion.getId(), Pageable.ofSize(2));
    Assertions.assertThat(allAnswers.getContent().get(0).getContent()).isEqualTo(answerSaved.getContent());

  }


  @Test
  void like_answer_should_be_saved() {

    final User testUser = User.builder()
        .username("testUser")
        .email("testUser@gmail.com")
        .provider(AuthProvider.google)
        .providerId("123")
        .build();
    final User savedUser = userRepository.save(testUser);

    final Question question = Question.builder()
        .content("question")
        .bookmarkCount(0L)
        .create_date(LocalDate.now())
        .userManager(savedUser)
        .build();
    final Question savedQuestion = questionRepository.save(question);

    final Answer answer = Answer.builder()
        .content("test")
        .liked(0L)
        .user(savedUser)
        .question(savedQuestion)
        .build();
    final Answer savedAnswer = answerRepository.save(answer);

    LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(savedUser)
        .answerManager(savedAnswer)
        .build();

    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);
    Assertions.assertThat(saved).isEqualTo(likeAnswer);

  }
}