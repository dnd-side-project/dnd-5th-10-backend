package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(staticName = "of")
@Getter
public class AnswerDto {
  private String content;
  private long liked;
  private long questionId;
  private long userId;

  public AnswerDto(Answer answer) {
    this.content = answer.getContent();
    this.liked = answer.getLiked();
    this.questionId = answer.getQuestionManager().getId();
    this.userId = answer.getUserManager().getId();
  }

  public Answer toEntity(User user, Question question) {
    return Answer.builder()
        .content(content)
        .liked(liked)
        .userManager(user)
        .questionManager(question)
        .build();
  }
}
