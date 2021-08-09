package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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


  public static QuestionResponseDto of(Question question) {
    final List<QuestionTagResponseDto> questionTagResponses = question.getQuestionTagList().stream()
        .map(e -> new QuestionTagResponseDto(e.getTagManager().getName())).collect(
            Collectors.toList());

    return QuestionResponseDto.builder().id(question.getId())
        .content(question.getContent())
        .bookmarkCount(question.getBookmarkCount())
        .email(question.getUserManager().getEmail())
        .username(question.getUserManager().getUsername())
        .tagList(questionTagResponses)
        .build();
  }
}
