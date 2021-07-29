package com.dnd10.iterview.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {

  private Long id;

  @NotBlank
  private String content;

  private Long bookmark_count;
  private LocalDate create_date;

  private String username; // 보안상 유저 이메일, 닉네임만
  private String email;

  private List<QuestionTagResponseDto> tagList = new ArrayList<>();
}
