package com.dnd10.iterview.dto;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class QuestionResponseDto {

  private Long id;

  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;

  private Long bookmarkCount;

  private String username; // 보안상 유저 이메일, 닉네임만
  private String email;

  private List<QuestionTagResponseDto> tagList = new ArrayList<>();
}
