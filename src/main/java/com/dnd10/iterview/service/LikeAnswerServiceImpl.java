package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.LikeAnswerRepository;
import com.dnd10.iterview.repository.UserRepository;
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
  public LikeAnswerDto create(LikeAnswerDto likeAnswerDto) {

    final User user = userRepository.findUserById(likeAnswerDto.getUserId())
        .orElseThrow(IllegalArgumentException::new);
    final Answer answer = answerRepository.findById(likeAnswerDto.getAnswerId())
        .orElseThrow(IllegalArgumentException::new);

    if (likeAnswerRepository.findByUserManagerAndAnswerManager(user, answer).isPresent()) {
      throw new IllegalArgumentException("이미 좋아요한 답변입니다.");
    }

    final LikeAnswer likeAnswer = LikeAnswer.builder()
        .userManager(user)
        .answerManager(answer)
        .build();
    final LikeAnswer saved = likeAnswerRepository.save(likeAnswer);

    return LikeAnswerDto.of(saved);
  }

  @Override
  public LikeAnswerDto delete(LikeAnswerDto likeAnswerDto) {
    final LikeAnswer likeAnswer = likeAnswerRepository
        .findByUserManager_IdAndAnswerManager_Id(likeAnswerDto.getUserId(),
            likeAnswerDto.getAnswerId()).orElseThrow(IllegalArgumentException::new);
    likeAnswerRepository.delete(likeAnswer);
    return LikeAnswerDto.of(likeAnswer);
  }

  @Override
  public Page<AnswerDto> getAllAnswerLiked(Long userId, Pageable pageable) {
    final Page<LikeAnswer> likeAnswerPage = likeAnswerRepository
        .findAllByUserManager_Id(userId, pageable);
    return likeAnswerPage.map(likeAnswer -> new AnswerDto(likeAnswer.getAnswerManager()));
  }
}
