package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor(staticName = "of")
@Getter
public class AnswerDto {

  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;
  private long liked;
  private long questionId;
  private long userId;

  public AnswerDto(Answer answer) {
    this.content = answer.getContent();
    this.liked = answer.getLiked();
    this.questionId = answer.getQuestion().getId();
    this.userId = answer.getUser().getId();
  }

  public Answer toEntity(User user, Question question) {
    return Answer.builder()
        .content(content)
        .liked(liked)
        .user(user)
        .question(question)
        .build();
  }
}
