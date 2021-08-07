package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import com.dnd10.iterview.dto.BookmarkResponseDto;
import com.dnd10.iterview.service.BookmarkService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
  /**
   * 북마크 폴더 생성 v
   * 내 북마크 폴더 조회 v
   * 북마크 폴더 이름 수정 v
   * 북마크 폴더 삭제 v
   */

  private final BookmarkService bookmarkService;

  @ApiOperation(value = "북마크 폴더 생성", notes = "<big>북마크 폴더를 생성</big>한다.")
  @PostMapping("")
  public ResponseEntity addBookmark(Principal principal, @RequestBody @Valid BookmarkRequestDto bookmarkRequestDto){
    BookmarkResponseDto dto = bookmarkService.addBookmark(principal, bookmarkRequestDto);

    return ResponseEntity.ok(dto);
  }

  @ApiOperation(value = "북마크 폴더 이름 변경", notes = "<big>북마크 폴더의 이름을 수정</big>한다.")
  @PatchMapping("/{bookmarkId}")
  public ResponseEntity updateBookmark(Principal principal,
      @RequestBody @Valid BookmarkRequestDto bookmarkRequestDto, @PathVariable Long bookmarkId) {
    BookmarkResponseDto dto = bookmarkService.updateBookmark(principal, bookmarkId, bookmarkRequestDto);

    return ResponseEntity.ok(dto);
  }

  @ApiOperation(value = "북마크 폴더 조회", notes = "<big>나의 북마크 폴더 리스트를 조회</big>한다.")
  @GetMapping("")
  public ResponseEntity getMyBookmark(Principal principal){ // todo: page 필요?
    List<BookmarkResponseDto> dtos = bookmarkService.getMyBookmark(principal);

    return ResponseEntity.ok(dtos);
  }

  @ApiOperation(value = "북마크 폴더 삭제", notes = "<big>북마크 폴더를 삭제</big> 한다.")
  @DeleteMapping("/{bookmarkId}")
  public ResponseEntity deleteBookmark(Principal principal, @PathVariable Long bookmarkId){
    bookmarkService.deleteBookmark(principal, bookmarkId);

    return ResponseEntity.ok().build();
  }

}
