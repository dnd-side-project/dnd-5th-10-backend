package com.dnd10.iterview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkResponseDto {

  private Long id;
  private String name;

  private String username; // 보안상 유저 이메일, 닉네임만
  private String email;

}
