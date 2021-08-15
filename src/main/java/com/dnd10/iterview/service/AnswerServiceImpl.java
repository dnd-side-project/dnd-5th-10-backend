package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerRequestDto;
import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.MyAnswerDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
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
  public AnswerResponseDto createAnswer(AnswerRequestDto answerRequestDto, Principal principal) {
    final Question question = questionRepository.findById(answerRequestDto.getQuestionId())
        .orElseThrow(IllegalArgumentException::new);
    final User user = userRepository.findUserByEmail(principal.getName())
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
  public Page<MyAnswerDto> getMyAnswers(Principal principal, Pageable pageable) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final Page<Answer> answers = answerRepository.findAllByUser(user, pageable);

    return answers.map(e -> new MyAnswerDto(e, QuestionResponseDto.of(e.getQuestion())));
  }

  @Override
  public List<AnswerResponseDto> saveAnswers(Principal principal, List<AnswerRequestDto> answers) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final List<Answer> savedAnswers = answerRepository
        .saveAll(answers.stream().map(e -> getAnswer(user, e)).collect(Collectors.toList()));
    return savedAnswers.stream().map(AnswerResponseDto::new).collect(Collectors.toList());
  }

  private Answer getAnswer(User user, AnswerRequestDto e) {
    final Question question = questionRepository.findById(e.getQuestionId())
        .orElseThrow(() -> new IllegalArgumentException("없는 문제가 있습니다."));
    return e.toEntity(user, question);
  }
}
