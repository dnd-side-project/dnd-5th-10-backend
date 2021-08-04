package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import java.security.Principal;

public interface BookmarkService {

  BookmarkRequestDto addBookmark(Principal principal, BookmarkRequestDto bookmarkRequestDto);

}
