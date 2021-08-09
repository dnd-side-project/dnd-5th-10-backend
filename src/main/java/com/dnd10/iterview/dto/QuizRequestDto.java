package com.dnd10.iterview.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class QuizRequestDto {
  private final List<String> tags;
  private final int size;

  public QuizRequestDto(List<String> tags, int size) {
    validate(tags, size);
    this.tags = tags;
    this.size = size;
  }

  private void validate(List<String> tags, int size) {
    if (tags.size() > 10) {
      throw new IndexOutOfBoundsException("태그 갯수는 0 - 10 이내 입니다.");
    }
    if (5 > size || size > 30) {
      throw new IllegalArgumentException("문제 출제 갯수는 5 - 30개 입니다.");
    }
  }
}
