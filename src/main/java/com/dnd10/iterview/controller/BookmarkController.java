package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import com.dnd10.iterview.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
  /**
   * 북마크 폴더 생성
   * 북마크 폴더 삭제
   * 북마크 폴더 이름 수정
   * 내 북마크 폴더 조회
   * 문제 북마크 폴더에 추가
   * 문제 북마크 폴더에서 삭제
   * 북마크 폴더 내 문제 조회 - page
   */

  private final BookmarkService bookmarkService;

  @ApiOperation(value = "북마크 폴더 생성", notes = "<big>북마크 폴더</big>를 생성한다.")
  @PostMapping("")
  public ResponseEntity addBookmark(Principal principal, @RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
    return ResponseEntity.ok(bookmarkService.addBookmark(principal, bookmarkRequestDto));
  }

}
