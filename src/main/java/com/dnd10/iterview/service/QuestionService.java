package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

  QuestionResponseDto getQuestion(Long questionId);
  QuestionResponseDto addQuestion(Principal principal, QuestionRequestDto requestDto);
  List<QuestionResponseDto> getAllQuestions(Pageable pageable);
  List<QuestionResponseDto> getSearchQuestions(String tagList, String sort);
  List<QuestionResponseDto> getMyAllQuestions(Principal princiapl, Pageable pageable);
  List<QuestionResponseDto> getQuiz();
}
