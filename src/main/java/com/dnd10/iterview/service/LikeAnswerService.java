package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.dto.LikeAnswerDto;
import com.dnd10.iterview.entity.LikeAnswer;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeAnswerService {

  LikeAnswerDto create(LikeAnswerDto likeAnswerDto);

  LikeAnswerDto delete(LikeAnswerDto likeAnswerDto);

  List<AnswerDto> getAllAnswerLiked(Long userId);
  Page<LikeAnswer> getAllAnswerLiked2(Long userId, Pageable pageable);
}
