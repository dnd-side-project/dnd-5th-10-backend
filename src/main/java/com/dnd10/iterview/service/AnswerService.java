package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {

  Page<AnswerDto> getAllAnswersByQuestion(Long id, Pageable pageable);

  AnswerDto createAnswer(AnswerDto answerDto);

  AnswerDto getAnswer(Long id);

  Page<AnswerDto> getMyAnswers(Principal principal, Pageable pageable);
}
