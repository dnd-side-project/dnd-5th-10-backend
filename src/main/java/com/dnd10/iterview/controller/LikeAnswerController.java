package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.LikeAnswerResponseDto;
import com.dnd10.iterview.service.LikeAnswerService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/answer/like")
public class LikeAnswerController {

  private final LikeAnswerService likeAnswerService;

  @ApiOperation(value = "답변 좋아요", notes = "<big>특정 답변을 좋아요</big> 처리.")
  @PostMapping
  public ResponseEntity<LikeAnswerResponseDto>  createLikeAnswer(@RequestBody @Valid LikeAnswerResponseDto likeAnswerResponseDto) {
    return ResponseEntity.ok(likeAnswerService.create(likeAnswerResponseDto));
  }

  @ApiOperation(value = "답변 좋아요 삭제", notes = "<big>특정 답변의 좋아요</big>을 삭제한다.")
  @DeleteMapping
  public ResponseEntity<LikeAnswerResponseDto>  DeleteLikeAnswer(@RequestBody @Valid LikeAnswerResponseDto likeAnswerResponseDto) {
    return ResponseEntity.ok(likeAnswerService.delete(likeAnswerResponseDto));
  }

  @ApiOperation(value = "내가 좋아한 답변을 조회", notes = "<big>내가 좋아요한 답변</big>을 조회한다.")
  @GetMapping("/mine")
  public ResponseEntity<Page<AnswerResponseDto>> getAllAnswersLiked(final Pageable pageable,
      Principal principal) {
    return ResponseEntity.ok(likeAnswerService.getAllAnswerLiked(principal,pageable));
  }

}
