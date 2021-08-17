package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.LikeAnswerResponseDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.LikeAnswerRepository;
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
public class LikeAnswerServiceImpl implements LikeAnswerService {

  private final LikeAnswerRepository likeAnswerRepository;
  private final UserRepository userRepository;
  private final AnswerRepository answerRepository;

  @Override
  public LikeAnswerResponseDto create(Long answerId, Principal principal) {

    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final Answer answer = answerRepository.findById(answerId)
        .orElseThrow(IllegalArgumentException::new);

    if (likeAnswerRepository.findByUserManagerAndAnswerManager(user, answer).isPresent()) {
      throw new IllegalArgumentException("이미 좋아요한 답변입니다.");
    }

    answer.likeUp();

    final LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(user)
        .answerManager(answer)
        .build();
    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);

    return LikeAnswerResponseDto.of(saved);
  }

  @Override
  public LikeAnswerResponseDto delete(Long answerId, Principal principal) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(IllegalArgumentException::new);
    final Answer answer = answerRepository.findById(answerId)
        .orElseThrow(IllegalArgumentException::new);

    answer.likeDown();

    final LikeAnswer likeAnswer = likeAnswerRepository
        .findByUserManagerAndAnswerManager(user, answer)
        .orElseThrow(() -> new IllegalArgumentException("해당 답변의 좋아요 기록이 없습니다."));

    likeAnswerRepository.delete(likeAnswer);
    return LikeAnswerResponseDto.of(likeAnswer);
  }

  @Override
  public Page<AnswerResponseDto> getAllAnswerLiked(Principal principal, Pageable pageable) {
    final User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("토큰 사용자가 가입되어있지 않습니다."));
    final Page<LikeAnswer> likeAnswerPage = likeAnswerRepository
        .findAllByUserManager_Id(user.getId(), pageable);
    return likeAnswerPage.map(likeAnswer -> new AnswerResponseDto(likeAnswer.getAnswerManager()));
  }
}
