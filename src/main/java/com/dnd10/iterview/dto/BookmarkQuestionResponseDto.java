package com.dnd10.iterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkQuestionResponseDto {
  private Long id;
  private QuestionResponseDto question;
  private Long bookmarkId;
}
