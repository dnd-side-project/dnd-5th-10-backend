package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import com.dnd10.iterview.repository.BookmarkQuestionRepository;
import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final BookmarkQuestionRepository bookmarkQuestionRepository;
  private final UserRepository userRepository;

  @Override
  public BookmarkRequestDto addBookmark(Principal principal, BookmarkRequestDto bookmarkRequestDto){

  }

}
