package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.service.AnswerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/answer")
public class AnswerController {

  AnswerService answerService;

  public AnswerController(AnswerService answerService) {
    this.answerService = answerService;
  }

  @PostMapping
  public AnswerDto submitAnswer(@RequestBody @Valid AnswerDto answerDto) {
    return answerService.createAnswer(answerDto);
  }

  @GetMapping("/all/{questionId}")
  public List<AnswerDto> getAllAnswers(@PathVariable Long questionId) {
    return answerService.getAllAnswers(questionId);
  }
}
