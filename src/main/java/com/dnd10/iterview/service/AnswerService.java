package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerRequestDto;
import com.dnd10.iterview.dto.AnswerResponseDto;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {

  Page<AnswerResponseDto> getAllAnswersByQuestion(Long id, Pageable pageable);

  AnswerResponseDto createAnswer(AnswerRequestDto answerRequestDto, Principal principal);

  AnswerResponseDto getAnswer(Long id);

  Page<AnswerResponseDto> getMyAnswers(Principal principal, Pageable pageable);

  List<AnswerResponseDto> saveAnswers(Principal principal, List<AnswerRequestDto> answers);

  AnswerResponseDto getMyAnswerByQuestion(Principal principal, Long id);
}
