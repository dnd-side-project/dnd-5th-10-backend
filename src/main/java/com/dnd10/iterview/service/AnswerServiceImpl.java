package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerRequestDto;
import com.dnd10.iterview.dto.AnswerResponseDto;
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
  public Page<AnswerResponseDto> getAllAnswersByQuestion(Long questionId, Pageable pageable) {

    final Question question = questionRepository.findById(questionId)
        .orElseThrow(IllegalArgumentException::new);
    final Page<Answer> answers = answerRepository.findAnswersByQuestion(question, pageable);

    return answers.map(AnswerResponseDto::new);
  }

  @Override
  public AnswerResponseDto createAnswer(AnswerRequestDto answerRequestDto) {
    final Question question = questionRepository.findById(answerRequestDto.getQuestionId())
        .orElseThrow(IllegalArgumentException::new);
    final User user = userRepository.findUserById(answerRequestDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    final Answer saved = answerRepository.save(answerRequestDto.toEntity(user, question));
    return new AnswerResponseDto(saved);
  }

  @Override
  public AnswerResponseDto getAnswer(Long id) {
    final Answer answer = answerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return new AnswerResponseDto(answer);
  }

  @Override
  public Page<AnswerResponseDto> getMyAnswers(Principal principal, Pageable pageable) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final Page<Answer> answers = answerRepository.findAllByUser(user, pageable);

    return answers.map(AnswerResponseDto::new);
  }
}
