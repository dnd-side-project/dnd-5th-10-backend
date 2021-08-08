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
  public LikeAnswerResponseDto create(LikeAnswerResponseDto likeAnswerResponseDto) {

    final User user = userRepository.findUserById(likeAnswerResponseDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    final Answer answer = answerRepository.findById(likeAnswerResponseDto.getAnswerId())
        .orElseThrow(IllegalArgumentException::new);

    if (likeAnswerRepository.findByUserManagerAndAnswerManager(user, answer).isPresent()) {
      throw new IllegalArgumentException("이미 좋아요한 답변입니다.");
    }

    final LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(user)
        .answerManager(answer)
        .build();
    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);

    return LikeAnswerResponseDto.of(saved);
  }

  @Override
  public LikeAnswerResponseDto delete(LikeAnswerResponseDto likeAnswerResponseDto) {
    final LikeAnswer likeAnswer = likeAnswerRepository
        .findByUserManager_IdAndAnswerManager_Id(likeAnswerResponseDto.getUserId(),
            likeAnswerResponseDto.getAnswerId()).orElseThrow(IllegalArgumentException::new);
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
