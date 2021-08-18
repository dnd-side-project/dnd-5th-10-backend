package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.LikeAnswerResponseDto;
import com.dnd10.iterview.dto.MyAnswerDto;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeAnswerService {

  LikeAnswerResponseDto create(Long answerId, Principal principal);

  LikeAnswerResponseDto delete(Long answerId, Principal principal);

  Page<MyAnswerDto> getAllAnswerLiked(Principal principal, Pageable pageable);
}
