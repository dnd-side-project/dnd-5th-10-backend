package com.dnd10.iterview.dto;

import java.util.List;
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

  private Long bookmarkCount; // 필요성?
  private List<String> tags;
}
