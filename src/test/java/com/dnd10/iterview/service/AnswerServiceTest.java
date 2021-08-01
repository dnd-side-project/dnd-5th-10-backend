package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
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
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

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


  @Test
  void answer_create_and_find_should_work() {

    final User testUser = User.builder()
        .username("testUser")
        .email("test@gmail.com")
        .provider(AuthProvider.google)
        .providerId("123")
        .build();
    userRepository.save(testUser);

    final Question question = Question.builder()
        .content("question")
        .bookmark_count(0L)
        .create_date(LocalDate.now())
        .userManager(testUser)
        .build();
    questionRepository.save(question);

    final Answer answer = Answer.builder()
        .content("test")
        .liked(0L)
        .userManager(testUser)
        .questionManager(question)
        .build();
    answerRepository.save(answer);

    final AnswerDto answerDto = AnswerDto.builder()
        .content(answer.getContent())
        .liked(answer.getLiked())
        .questionId(question.getId())
        .userId(testUser.getId())
        .build();
    final AnswerDto answerSaved = answerService.createAnswer(answerDto);

    final List<AnswerDto> allAnswers = answerService.getAllAnswersByQuestion(1L,"default");
    Assertions.assertThat(allAnswers.get(0).getContent()).isEqualTo(answerSaved.getContent());

  }


  @Test
  void like_answer_should_saved() {

    final User testUser = User.builder()
        .username("testUser")
        .email("test@gmail.com")
        .provider(AuthProvider.google)
        .providerId("123")
        .build();
    userRepository.save(testUser);

    final Question question = Question.builder()
        .content("question")
        .bookmark_count(0L)
        .create_date(LocalDate.now())
        .userManager(testUser)
        .build();
    questionRepository.save(question);

    final Answer answer = Answer.builder()
        .content("test")
        .liked(0L)
        .userManager(testUser)
        .questionManager(question)
        .build();
    answerRepository.save(answer);

    LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(testUser)
        .answerManager(answer)
        .build();

    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);
    Assertions.assertThat(saved).isEqualTo(likeAnswer);

  }
}