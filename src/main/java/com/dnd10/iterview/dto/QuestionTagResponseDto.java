package com.dnd10.iterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionTagResponseDto {
  // todo: question과 user tag dto를 나눌 필요성이 있는지 생각해보기
  private String tagTitle;
}
