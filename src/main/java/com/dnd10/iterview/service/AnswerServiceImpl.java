package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerServiceImpl implements AnswerService {

  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  @Override
  public Page<AnswerDto> getAllAnswersByQuestion(Long questionId, Pageable pageable) {

    final Question question = questionRepository.findById(questionId)
        .orElseThrow(IllegalArgumentException::new);
    final Page<Answer> answers = answerRepository.findAnswersByQuestion(question, pageable);

    return answers.map(AnswerDto::new);
  }

  @Override
  public AnswerDto createAnswer(AnswerDto answerDto) {
    final Question question = questionRepository.findById(answerDto.getQuestionId())
        .orElseThrow(IllegalArgumentException::new);
    final User user = userRepository.findUserById(answerDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    answerRepository.save(answerDto.toEntity(user, question));
    return answerDto;
  }

  @Override
  public AnswerDto getAnswer(Long id) {
    final Answer answer = answerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return new AnswerDto(answer);
  }

  @Override
  public Page<AnswerDto> getMyAnswers(Principal principal) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final Page<Answer> answers = answerRepository.findAllByUser(user);

    return answers.map(AnswerDto::new);
  }
}
