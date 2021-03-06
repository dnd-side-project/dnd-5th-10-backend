package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;


@AllArgsConstructor
@Builder
@Getter
public class AnswerRequestDto {

  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;
  private long questionId;

  public AnswerRequestDto(Answer answer) {
    this.content = answer.getContent();
    this.questionId = answer.getQuestion().getId();
  }

  public Answer toEntity(User user, Question question) {
    return Answer.builder()
        .content(content)
        .liked(0L)
        .user(user)
        .question(question)
        .build();
  }
}
