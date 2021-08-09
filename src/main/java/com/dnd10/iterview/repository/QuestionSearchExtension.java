package com.dnd10.iterview.repository;

import com.dnd10.iterview.dto.QuizRequestDto;
import com.dnd10.iterview.entity.Question;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface QuestionSearchExtension {

  Page<Question> findWithTags(List<String> tagList, String keyword, Pageable pageable);
  //List<Question> findWithTagsRandom(List<String> tagList);

  List<Question> findWithTags(QuizRequestDto quizRequestDto);
}
