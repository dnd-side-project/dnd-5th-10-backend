package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.service.LikeAnswerService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/like/answer")
public class LikeAnswerController {

  private final LikeAnswerService likeAnswerService;

  @ApiOperation(value = "답변 좋아요", notes = "<big>특정 답변을 좋아요</big> 처리.")
  @PostMapping
  public LikeAnswerDto createLikeAnswer(@RequestBody @Valid LikeAnswerDto likeAnswerDto) {
    return likeAnswerService.create(likeAnswerDto);
  }

  @ApiOperation(value = "답변 좋아요 삭제", notes = "<big>특정 답변의 좋아요</big>을 삭제한다.")
  @DeleteMapping
  public LikeAnswerDto DeleteLikeAnswer(@RequestBody @Valid LikeAnswerDto likeAnswerDto) {
    return likeAnswerService.delete(likeAnswerDto);
  }

  @ApiOperation(value = "내가 좋아한 답변을 조회", notes = "<big>내가 좋아요한 답변</big>을 조회한다.")
  @GetMapping("/page/{userId}")
  public Page<AnswerDto> getAllAnswersLiked(final Pageable pageable, @PathVariable Long userId) {
    return likeAnswerService.getAllAnswerLiked(userId,pageable);
  }

}
