package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import com.dnd10.iterview.entity.User;
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

  @NotBlank
  private String content;

  private Long bookmark_count;
  private LocalDate create_date;

  private UserDto user;

  private List<QuestionTagResponseDto> tagList = new ArrayList<>();
}
