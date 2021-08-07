package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.BookmarkQuestionResponseDto;
import com.dnd10.iterview.service.BookmarkQuestionService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmarkQuestion")
@RequiredArgsConstructor
public class BookmarkQuestionController {
  /**
   * 문제 북마크 폴더에 추가
   * 문제 북마크 폴더에서 삭제
   * 북마크 폴더 내 문제 조회 - page
   */

  private final BookmarkQuestionService bookmarkQuestionService;

  @ApiOperation(value = "문제 북마크", notes = "<big>문제를 북마크 폴더에 추가</big>한다.")
  @PostMapping("/{questionId}/{bookmarkId}")
  public ResponseEntity addBookmark(Principal principal, @PathVariable Long questionId, @PathVariable Long bookmarkId){
    BookmarkQuestionResponseDto dto = bookmarkQuestionService.addBookmarkQuestion(principal, questionId, bookmarkId);

    return ResponseEntity.ok(dto);
  }
}
