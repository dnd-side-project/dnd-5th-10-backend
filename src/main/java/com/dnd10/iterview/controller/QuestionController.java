package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question")
public class QuestionController {

  QuestionService questionService;

  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

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
