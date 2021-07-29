package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import java.security.Principal;
import java.util.List;

public interface QuestionService {

  QuestionResponseDto getQuestion(Long questionId);
  QuestionResponseDto addQuestion(Principal principal, QuestionRequestDto requestDto);
  List<QuestionResponseDto> getSearchQuestions();
  List<QuestionResponseDto> getQuiz();
}
