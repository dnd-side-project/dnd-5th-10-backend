package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import com.dnd10.iterview.util.Order;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
  public List<AnswerDto> getAllAnswers(Long id, String order) {
    List<Answer> answers;
    if (order.equals(Order.DESC.getOrder())) {
      answers = answerRepository.findAllByQuestionManager_IdOrderByLikedDesc(id).orElseThrow(IllegalArgumentException::new);
    } else {
      answers = answerRepository.findAllByQuestionManager_Id(id).orElseThrow(IllegalArgumentException::new);
    }
    return answers.stream().map(AnswerDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public AnswerDto createAnswer(AnswerDto answerDto) {
    final Question question = questionRepository.findById(answerDto.getQuestionId())
        .orElseThrow(IllegalArgumentException::new);
    final User user = userRepository.findUserById(answerDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    answerRepository.save(answerDto.toEntity(user,question));
    return answerDto;
  }

}
