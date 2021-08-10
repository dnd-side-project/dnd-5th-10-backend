package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkQuestionDto;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookmarkQuestionService {
  BookmarkQuestionDto addBookmarkQuestion(Principal principal, Long questionId, Long bookmarkFolderId);
  List<BookmarkQuestionDto> getBookmarkQuestion(Principal principal, Long bookmarkFolderId, Pageable pageable);
  void deleteBookmarkQuestion(Principal principal, Long bookmarkQuestionId);
}
