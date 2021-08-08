package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.LikeAnswer;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeAnswerResponseDto {

  @NotNull
  private final Long userId;
  @NotNull
  private final Long answerId;

  public LikeAnswerResponseDto(Long userId, Long answerId) {
    this.userId = userId;
    this.answerId = answerId;
  }

  public static LikeAnswerResponseDto of(LikeAnswer likeAnswer) {
    return new LikeAnswerResponseDto(likeAnswer.getUserManager().getId(),
        likeAnswer.getAnswerManager().getId());
  }

}
