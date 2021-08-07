package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.BookmarkQuestionDto;
import com.dnd10.iterview.service.BookmarkQuestionService;
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
@RequestMapping("/api/v1/bookmarkQuestion")
@RequiredArgsConstructor
public class BookmarkQuestionController {
  /**
   * 문제 북마크 폴더에 추가 v
   * 문제 북마크 폴더에서 삭제 v
   * 북마크 폴더 내 문제 조회 - page v
   */

  private final BookmarkQuestionService bookmarkQuestionService;

  @ApiOperation(value = "문제 북마크하기", notes = "<big>문제를 북마크 폴더에 추가</big>한다.")
  @PostMapping("/{questionId}/{bookmarkId}")
  public ResponseEntity addBookmarkQuestion(Principal principal, @PathVariable Long questionId, @PathVariable Long bookmarkId){
    BookmarkQuestionDto dto = bookmarkQuestionService.addBookmarkQuestion(principal, questionId, bookmarkId);

    return ResponseEntity.ok(dto);
  }

  @ApiOperation(value = "북마크 폴더 내 북마크한 문제 조회", notes = "<big>북마크 폴더에서 북마크했던 문제들을 조회</big>한다.")
  @GetMapping("/{bookmarkId}")
  public ResponseEntity getBookmarkQuestion(Principal principal, @PathVariable Long bookmarkId,
      @PageableDefault(size = 5, sort = "bookmarkCount", direction = Sort.Direction.DESC) Pageable pageable){
    List<BookmarkQuestionDto> dtoList = bookmarkQuestionService.getBookmarkQuestion(principal, bookmarkId, pageable);

    return ResponseEntity.ok(dtoList);
  }

  @ApiOperation(value = "문제 북마크 취소", notes = "<big>북마크 폴더에서 북마크한 문제를 삭제</big>한다.")
  @DeleteMapping("/{bookmarkQuestionId}")
  public ResponseEntity deleteBookmarkQuestion(Principal principal, @PathVariable Long bookmarkQuestionId){
    bookmarkQuestionService.deleteBookmarkQuestion(principal, bookmarkQuestionId);

    return ResponseEntity.ok().build();
  }
}
