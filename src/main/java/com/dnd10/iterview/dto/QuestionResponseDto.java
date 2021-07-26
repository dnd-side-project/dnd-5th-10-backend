package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.User;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class QuestionResponseDto {

  @NotBlank
  private String content;

  private Long bookmark_count;
  private LocalDate create_date;

  private UserDto user;

}
