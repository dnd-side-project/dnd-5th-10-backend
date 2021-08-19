package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
public class HitAnswerResponseDto extends BaseTimeEntityDto {

  private long id;
  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;
  private long liked;
  private long questionId;
  private String userName;
  private String questionContent;

  public HitAnswerResponseDto(long id,
      @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000") String content,
      long liked, long questionId, String userName, String questionContent) {
    super();
    this.id = id;
    this.content = content;
    this.liked = liked;
    this.questionId = questionId;
    this.userName = userName;
    this.questionContent = questionContent;
  }

  public HitAnswerResponseDto(Answer answer) {
    super(answer.getCreatedDate(), answer.getModifiedDate());
    this.id = answer.getId();
    this.content = answer.getContent();
    this.liked = answer.getLiked();
    this.questionId = answer.getQuestion().getId();
    this.userName = answer.getUser().getUsername();
    this.questionContent = answer.getQuestion().getContent();
  }

}
