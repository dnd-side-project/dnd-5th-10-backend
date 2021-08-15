package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class MyAnswerDto {

  private long id;
  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;
  private long liked;
  private QuestionResponseDto question;

  public MyAnswerDto(Answer answer, QuestionResponseDto question) {
    this.id = answer.getId();
    this.content = answer.getContent();
    this.liked = answer.getLiked();
    this.question = question;
  }
}
