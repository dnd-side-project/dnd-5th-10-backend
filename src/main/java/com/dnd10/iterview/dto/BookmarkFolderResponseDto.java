package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.BookmarkFolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkFolderResponseDto {

  private Long id;
  private String name;

  private String username; // 보안상 유저 이메일, 닉네임만
  private String email;

  public BookmarkFolderResponseDto(BookmarkFolder bookmarkFolder){
    this.id = bookmarkFolder.getId();
    this.name = bookmarkFolder.getName();
    this.username = bookmarkFolder.getUserManager().getUsername();
    this.email = bookmarkFolder.getUserManager().getEmail();
  }

}
