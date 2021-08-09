package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto extends BaseTimeEntityDto {

  private Long id;

  @Length(min = 20, max = 1000, message = "content length should be 20 ~ 1000")
  private String content;

  private Long bookmarkCount;

  private String username; // 보안상 유저 이메일, 닉네임만
  private String email;

  private AnswerResponseDto mostLikedAnswer;

  private List<QuestionTagResponseDto> tagList = new ArrayList<>();

  public QuestionResponseDto(Question question){
    super(question.getCreatedDate(), question.getModifiedDate());

    List<QuestionTagResponseDto> dtoList = new ArrayList<>();
    for(QuestionTag t : question.getQuestionTagList()){
      QuestionTagResponseDto dto = QuestionTagResponseDto.builder()
          .tagTitle(t.getTagManager().getName())
          .build();

      dtoList.add(dto);
    }

    this.id = question.getId();
    this.content = question.getContent();
    this.bookmarkCount = question.getBookmarkCount();
    this.username = question.getUserManager().getUsername();
    this.email = question.getUserManager().getEmail();
    this.tagList = dtoList;
  }

  public void setMostLikedAnswer(AnswerResponseDto answerDto){
    this.mostLikedAnswer = answerDto;
  }
}
