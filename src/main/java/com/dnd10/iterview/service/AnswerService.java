package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import java.util.List;

public interface AnswerService {

  List<AnswerDto> getAllAnswers(Long id, String order);

  AnswerDto createAnswer(AnswerDto answerDto);

}
