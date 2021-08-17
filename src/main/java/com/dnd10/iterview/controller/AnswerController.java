package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.AnswerRequestDto;
import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.MyAnswerDto;
import com.dnd10.iterview.service.AnswerService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
  public AnswerResponseDto submitAnswer(@RequestBody @Valid AnswerRequestDto answerRequestDto, Principal principal) {
    return answerService.createAnswer(answerRequestDto, principal);
  }

  @ApiOperation(value = "문제의 답변 조회", notes = "<big>한 문제의 모든 답변</big>을 조회한다.")
  @GetMapping("/question/{questionId}")
  public ResponseEntity<Page<AnswerResponseDto>> getAllAnswersByQuestion(
      @PathVariable Long questionId, @PageableDefault(size = 5, sort = "liked",
      direction = Sort.Direction.DESC) Pageable pageable) {

    return ResponseEntity.ok(answerService.getAllAnswersByQuestion(questionId, pageable));
  }

  @ApiOperation(value = "답변을 조회", notes = "답변ID로 <big>답변</big>을 조회한다.")
  @GetMapping("/{answerId}")
  public ResponseEntity<AnswerResponseDto> getAnswer(@PathVariable Long answerId) {

    return ResponseEntity.ok(answerService.getAnswer(answerId));
  }

  @ApiOperation(value = "내가 한 답변을 조회", notes = "<big>내가 한 답변</big>을 조회한다.")
  @GetMapping("/mine")
  public ResponseEntity<Page<MyAnswerDto>> getMyAnswers(Principal principal,
      @PageableDefault(size = 5, sort = "liked",
          direction = Sort.Direction.DESC) Pageable pageable) {

    return ResponseEntity.ok(answerService.getMyAnswers(principal, pageable));
  }


  @ApiOperation(value = "답변 리스트를 insert")
  @PostMapping("/")
  public ResponseEntity<List<AnswerResponseDto>> submitAnswers(Principal principal,
      @RequestBody @NotEmpty(message = "답변 리스트 길이가 0이어선 안됩니다.") List<AnswerRequestDto> answers) {

    return ResponseEntity.ok(answerService.saveAnswers(principal, answers));
  }

  @ApiOperation(value = "한 문제에 대한 내 답변 조회")
  @GetMapping("{questionId}/mine")
  public ResponseEntity getMyAnswerByQuestion(Principal principal, @PathVariable Long questionId){
    return ResponseEntity.ok(answerService.getMyAnswerByQuestion(principal, questionId));
  }
}
