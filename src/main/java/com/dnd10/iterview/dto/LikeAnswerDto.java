package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.LikeAnswer;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LikeAnswerDto {

  @NotBlank
  private final Long userId;
  @NotBlank
  private final Long answerId;

  public LikeAnswerDto(Long userId, Long answerId) {
    this.userId = userId;
    this.answerId = answerId;
  }

  public static LikeAnswerDto of(LikeAnswer likeAnswer) {
    return new LikeAnswerDto(likeAnswer.getUserManager().getId(),
        likeAnswer.getAnswerManager().getId());
  }

}
