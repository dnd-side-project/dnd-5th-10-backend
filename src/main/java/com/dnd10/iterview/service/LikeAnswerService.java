package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerDto;
import com.dnd10.iterview.dto.LikeAnswerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeAnswerService {

  LikeAnswerDto create(LikeAnswerDto likeAnswerDto);

  LikeAnswerDto delete(LikeAnswerDto likeAnswerDto);

  Page<AnswerDto> getAllAnswerLiked(Long userId, Pageable pageable);
}
