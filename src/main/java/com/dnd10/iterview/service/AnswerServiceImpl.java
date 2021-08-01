package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.LikeAnswerRepository;
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
  private final LikeAnswerRepository likeAnswerRepository;
  @Override
  public List<AnswerDto> getAllAnswersByQuestion(Long id, String order) {
    List<Answer> answers;
    if (order.equals(Order.DESC.getOrder())) {
      answers = answerRepository.findAllByQuestionManager_IdOrderByLikedDesc(id)
          .orElseThrow(IllegalArgumentException::new);
    } else {
      answers = answerRepository.findAllByQuestionManager_Id(id)
          .orElseThrow(IllegalArgumentException::new);
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
    answerRepository.save(answerDto.toEntity(user, question));
    return answerDto;
  }

  @Override
  public List<AnswerDto> getAllAnswerLiked(Long userId) {
    final List<Answer> likedAnswers = likeAnswerRepository.findAllAnswersLiked(userId)
        .orElseThrow(IllegalAccessError::new);
    return likedAnswers.stream().map(AnswerDto::new)
        .collect(Collectors.toList());
  }

  @Override
  public AnswerDto getAnswer(Long id) {
    final Answer answer = answerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    return new AnswerDto(answer);
  }
}
