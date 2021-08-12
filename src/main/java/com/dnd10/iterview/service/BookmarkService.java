package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkDto;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookmarkService {
  BookmarkDto addBookmark(Principal principal, Long questionId);
  List<BookmarkDto> getBookmark(Principal principal, Pageable pageable);
  void deleteBookmark(Principal principal, Long bookmarkId);
}
