package com.dnd10.iterview.controller;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
  /**
  * 한 문제 조회하기 v
  * 새로운 문제 생성 v
  * 문제 리스트 검색(최신순, 인기순), page 적용. (필요 시 단어 검색도 적용) v
  * 내가 한 질문 가져오기 v
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

  @ApiOperation(value = "모든 문제 리스트", notes = "<big>구분 없이 모든 문제 리스트</big>를 반환한다.")
  @GetMapping("/all")
  public ResponseEntity getAllQuestions(@PageableDefault(size = 5, sort = "bookmarkCount", // todo: 최신순 변경
      direction = Sort.Direction.DESC) Pageable pageable){
    List<QuestionResponseDto> questionList = questionService.getAllQuestions(pageable);

    return ResponseEntity.ok(questionList);
  }

  @ApiOperation(value = "문제 리스트 검색", notes = "<big>키워드에 따라 문제 리스트</big>를 반환한다.")
  @GetMapping("/search")
  public ResponseEntity getSearchQuestions(@RequestParam("tags") List<String> tagList, @RequestParam("keyword") String keyword,
      @PageableDefault(size = 5, sort = "bookmarkCount", direction = Sort.Direction.DESC) Pageable pageable){
    List<QuestionResponseDto> questionList = questionService.getSearchQuestions(tagList, keyword, pageable);

    return ResponseEntity.ok(questionList);
  }

  @ApiOperation(value = "내가 만든 문제 리스트 검색", notes = "<big>내가 만든 모든 문제</big>를 반환한다.")
  @GetMapping("/mine")
  public ResponseEntity getMyAllQuestions(Principal principal, @PageableDefault(size = 5, sort = "bookmarkCount",
      direction = Sort.Direction.DESC) Pageable pageable){
    List<QuestionResponseDto> questionList = questionService.getMyAllQuestions(principal, pageable);

    return ResponseEntity.ok(questionList);
  }

  @ApiOperation(value = "퀴즈 만들기", notes = "<big>키워드, 개수에 따라 퀴즈</big>를 생성하고 반환한다.")
  @GetMapping("/quiz")
  public ResponseEntity getQuiz(){
    List<QuestionResponseDto> questionList = questionService.getQuiz();

    return ResponseEntity.ok(questionList);
  }

}
