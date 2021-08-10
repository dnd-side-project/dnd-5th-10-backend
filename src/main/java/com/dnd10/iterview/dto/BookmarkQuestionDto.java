package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.BookmarkQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkQuestionDto extends BaseTimeEntityDto {
  private Long id;
  private QuestionResponseDto question;
  private Long bookmarkFolderId;

  public BookmarkQuestionDto(BookmarkQuestion bookmarkQuestion){
    super(bookmarkQuestion.getCreatedDate(), bookmarkQuestion.getModifiedDate());

    this.id = bookmarkQuestion.getId();
    this.question = new QuestionResponseDto(bookmarkQuestion.getQuestionManager());
    this.bookmarkFolderId = bookmarkQuestion.getBookmarkFolderManager().getId();
  }
}
