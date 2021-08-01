package com.dnd10.iterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDto {

  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;

  private Long bookmark_count; // 필요성?
  private String tags;
}
