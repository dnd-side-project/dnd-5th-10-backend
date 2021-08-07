package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkQuestionResponseDto;
import java.security.Principal;

public interface BookmarkQuestionService {
  BookmarkQuestionResponseDto addBookmarkQuestion(Principal principal, Long questionId, Long bookmarkId);

}
