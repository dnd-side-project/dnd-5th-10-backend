package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.LikeAnswerRepository;
import com.dnd10.iterview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeAnswerServiceImpl implements LikeAnswerService {

  private final UserService userService;
  private final AnswerService answerService;
  private final LikeAnswerRepository likeAnswerRepository;

  private final UserRepository userRepository;
  private final AnswerRepository answerRepository;

  @Override
  public LikeAnswerDto add(LikeAnswerDto likeAnswerDto) {

    final User user = userRepository.findUserById(likeAnswerDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    final Answer answer = answerRepository.findById(likeAnswerDto.getAnswerId())
        .orElseThrow(IllegalArgumentException::new);

    final LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(user)
        .answerManager(answer)
        .build();
    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);

    return LikeAnswerDto.of(saved);
  }
}
