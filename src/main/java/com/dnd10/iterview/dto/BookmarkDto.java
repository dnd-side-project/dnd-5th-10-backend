package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto extends BaseTimeEntityDto {
  private Long id;
  private QuestionResponseDto question;
  private String email;

  public BookmarkDto(Bookmark bookmark){
    super(bookmark.getCreatedDate(), bookmark.getModifiedDate());

    this.id = bookmark.getId();
    this.question = new QuestionResponseDto(bookmark.getQuestion());
    this.email = bookmark.getUserManager().getEmail();
  }
}
