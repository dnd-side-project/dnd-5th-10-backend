package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.BookmarkDto;
import com.dnd10.iterview.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
  /**
   * 문제 북마크
   * 문제 북마크 취소
   * 내가 북마크 한 문제 보기
   */

  private final BookmarkService bookmarkService;

  @ApiOperation(value = "문제 북마크하기", notes = "<big>문제를 북마크</big>한다.")
  @PostMapping("/{questionId}")
  public ResponseEntity addBookmark(Principal principal, @PathVariable Long questionId){
    BookmarkDto dto = bookmarkService.addBookmark(principal, questionId);

    return ResponseEntity.ok(dto);
  }

  @ApiOperation(value = "내가 북마크한 문제 조회", notes = "<big>북마크했던 문제들을 조회</big>한다.")
  @GetMapping("/mine")
  public ResponseEntity getBookmark(Principal principal,
      @PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
    List<BookmarkDto> dtoList = bookmarkService.getBookmark(principal, pageable);

    return ResponseEntity.ok(dtoList);
  }

  @ApiOperation(value = "문제 북마크 취소", notes = "<big>북마크한 문제를 취소</big>한다.")
  @DeleteMapping("/{bookmarkId}")
  public ResponseEntity deleteBookmarkQuestion(Principal principal, @PathVariable Long bookmarkId){
    bookmarkService.deleteBookmark(principal, bookmarkId);

    return ResponseEntity.ok().build();
  }
}
