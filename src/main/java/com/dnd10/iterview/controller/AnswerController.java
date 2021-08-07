package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.service.AnswerService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/answer")
public class AnswerController {

  /**
   * 문제당 가장 인기있는 답변
   */
  private final AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PostMapping
  public AnswerDto submitAnswer(@RequestBody @Valid AnswerDto answerDto) {
    return answerService.createAnswer(answerDto);
  }

  @ApiOperation(value = "문제의 답변 조회", notes = "<big>한 문제의 모든 답변</big>을 조회한다.")
  @GetMapping("/question/{questionId}")
  public Page<AnswerDto> getAllAnswersByQuestion(@PathVariable Long questionId, @PageableDefault(size = 5, sort = "liked",
      direction = Sort.Direction.DESC) Pageable pageable) {
    return answerService.getAllAnswersByQuestion(questionId, pageable);
  }


  @GetMapping("/{answerId}")
  public AnswerDto getAnswer(@PathVariable Long answerId) {
    return answerService.getAnswer(answerId);
  }
}
