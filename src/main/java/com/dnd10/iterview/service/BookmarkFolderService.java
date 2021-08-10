package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkFolderRequestDto;
import com.dnd10.iterview.dto.BookmarkFolderResponseDto;
import java.security.Principal;
import java.util.List;

public interface BookmarkFolderService {

  BookmarkFolderResponseDto addBookmarkFolder(Principal principal, BookmarkFolderRequestDto bookmarkFolderRequestDto);
  BookmarkFolderResponseDto updateBookmarkFolder(Principal principal, Long bookmarkId, BookmarkFolderRequestDto bookmarkFolderRequestDto);
  List<BookmarkFolderResponseDto> getMyBookmarkFolder(Principal principal);
  void deleteBookmarkFolder(Principal principal, Long bookmarkId);
}
