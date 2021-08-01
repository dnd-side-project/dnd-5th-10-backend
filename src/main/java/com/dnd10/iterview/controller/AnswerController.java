package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.service.AnswerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @ApiOperation(value = "문제의 답변 조회", notes = "<big>한 문제의 모든 답변</big>을 조회한다.")
  @ApiImplicitParams(
      {
          @ApiImplicitParam(
              name = "sort"
              , value = "정렬 방식 (desc : 좋아요 내림차순)"
              , dataType = "string"
              , paramType = "query"
              , defaultValue = "default"
          )
      }
  )
  @GetMapping("/all/{questionId}")
  public List<AnswerDto> getAllAnswers(@PathVariable Long questionId,
      @RequestParam("sort") String order) {
    order = StringUtils.defaultString(order, "default");
    return answerService.getAllAnswersByQuestion(questionId, order);
  }
  @GetMapping("/all/{userId}")
  public List<AnswerDto> getAllAnswersLiked(@PathVariable Long userId,
      @RequestParam("sort") String order) {
    order = StringUtils.defaultString(order, "default");
    return answerService.getAllAnswersByQuestion(userId, order);
  }
}
