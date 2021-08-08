package com.dnd10.iterview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;


@Getter
public abstract class BaseTimeEntityDto {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedDate;

  public BaseTimeEntityDto(LocalDateTime createdDate, LocalDateTime modifiedDate) {
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  public BaseTimeEntityDto() {
  }
}
