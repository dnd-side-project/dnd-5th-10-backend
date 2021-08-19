package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.QuestionTag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@AllArgsConstructor
public class HitAnswerResponseDto extends BaseTimeEntityDto {

  private long id;
  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;
  private long liked;
  private long questionId;
  private String userName;
  private String questionContent;
  private List<QuestionTagResponseDto> tags;

  public HitAnswerResponseDto(Answer answer) {
    super(answer.getCreatedDate(), answer.getModifiedDate());
    this.id = answer.getId();
    this.content = answer.getContent();
    this.liked = answer.getLiked();
    this.questionId = answer.getQuestion().getId();
    this.userName = answer.getUser().getUsername();
    this.questionContent = answer.getQuestion().getContent();

    final List<QuestionTag> questionTagList = answer.getQuestion().getQuestionTagList();
    this.tags = questionTagList.stream()
        .map(e -> new QuestionTagResponseDto(e.getTagManager().getName())).collect(
            Collectors.toList());
  }

}
