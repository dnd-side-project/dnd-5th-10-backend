package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import com.dnd10.iterview.dto.BookmarkResponseDto;
import java.security.Principal;
import java.util.List;

public interface BookmarkService {

  BookmarkResponseDto addBookmark(Principal principal, BookmarkRequestDto bookmarkRequestDto);
  BookmarkResponseDto updateBookmark(Principal principal, Long bookmarkId, BookmarkRequestDto bookmarkRequestDto);
  List<BookmarkResponseDto> getMyBookmark(Principal principal);
  void deleteBookmark(Principal principal, Long bookmarkId);
}
