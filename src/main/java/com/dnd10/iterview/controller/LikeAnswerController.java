package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.service.LikeAnswerService;
import java.util.List;
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

  @PostMapping
  public LikeAnswerDto createLikeAnswer(@RequestBody @Valid LikeAnswerDto likeAnswerDto) {
    return likeAnswerService.create(likeAnswerDto);
  }

  @DeleteMapping
  public LikeAnswerDto DeleteLikeAnswer(@RequestBody @Valid LikeAnswerDto likeAnswerDto) {
    return likeAnswerService.delete(likeAnswerDto);
  }

  @GetMapping("/{userId}")
  public List<AnswerDto> getAllAnswersLiked(@PathVariable Long userId) {
    return likeAnswerService.getAllAnswerLiked(userId);
  }
  @GetMapping("/page/{userId}")
  public Page<LikeAnswer> getAllAnswersLiked2(final Pageable pageable, @PathVariable Long userId) {
    return likeAnswerService.getAllAnswerLiked2(userId,pageable);
  }

}
