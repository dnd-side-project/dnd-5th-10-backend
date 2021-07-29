package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
  /**
  * 한 문제 조회하기
  * 새로운 문제 생성
  * 문제 리스트 조회하기(최신순, 인기순)
  * 퀴즈 생성(특정 태그 문제 중 일정 숫자만큼 랜덤하게 response)
  */

  private final QuestionService questionService;

  @ApiOperation(value = "문제 조회", notes = "<big>문제 정보</big>를 반환한다.")
  @GetMapping("/{questionId}")
  public ResponseEntity getQuestion(@PathVariable Long questionId) {
    QuestionResponseDto dto = questionService.getQuestion(questionId);

    return ResponseEntity.ok(dto);
  }

  @ApiOperation(value = "문제 생성", notes = "<big>새로운 문제</big>를 생성하고 반환한다.")
  @PostMapping("")
  public ResponseEntity addQuestion(Principal principal, @RequestBody @Valid QuestionRequestDto requestDto){
    QuestionResponseDto dto = questionService.addQuestion(principal, requestDto);

    return ResponseEntity.ok(dto);
  }

}
