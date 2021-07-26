package com.dnd10.iterview.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class QuestionRequestDto {

  @NotBlank
  private String content;

  private Long bookmark_count;
}
