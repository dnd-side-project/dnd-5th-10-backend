package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.service.LikeAnswerService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public LikeAnswerDto addLikeAnswer(@RequestBody @Valid LikeAnswerDto likeAnswerDto) {
    return likeAnswerService.add(likeAnswerDto);
  }
}
