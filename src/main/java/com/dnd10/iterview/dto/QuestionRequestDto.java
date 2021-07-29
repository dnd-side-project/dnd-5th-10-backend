package com.dnd10.iterview.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

  @NotBlank
  private String content;

  private Long bookmark_count; // 필요성?
  private String tags;
}
