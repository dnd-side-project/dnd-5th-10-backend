package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkRequestDto;
import com.dnd10.iterview.dto.BookmarkResponseDto;
import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.BookmarkQuestionRepository;
import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
  public BookmarkResponseDto addBookmark(Principal principal, BookmarkRequestDto bookmarkRequestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    if(bookmarkRepository.existsByNameAndUserManager(bookmarkRequestDto.getName(), user)){
      throw new IllegalArgumentException("동일한 이름의 북마크가 이미 존재합니다.");
    } // 동일한 이름 북마크 생성 안되도록.

    Bookmark bookmark = Bookmark.builder()
        .name(bookmarkRequestDto.getName())
        .userManager(user).build();

    Bookmark saved = bookmarkRepository.save(bookmark);

    return generateBookmarkResponseDto(saved);
  }

  @Override
  public BookmarkResponseDto updateBookmark(Principal principal, Long bookmarkId, BookmarkRequestDto bookmarkRequestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    if(bookmarkRepository.existsByNameAndUserManager(bookmarkRequestDto.getName(), user)){
      throw new IllegalArgumentException("동일한 이름의 북마크가 이미 존재합니다.");
    } // 동일한 이름 북마크 생성 안되도록.

    if(!bookmark.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    } // 다른 유저가 북마크 변경 못하도록.

    bookmark.changeName(bookmarkRequestDto.getName());

    return generateBookmarkResponseDto(bookmark);
  }

  @Override
  public List<BookmarkResponseDto> getMyBookmark(Principal principal){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserManager(user);

    return generateBookmarkResponseDtoList(bookmarkList);
  }

  private BookmarkResponseDto generateBookmarkResponseDto(Bookmark bookmark){
    BookmarkResponseDto dto = BookmarkResponseDto.builder()
        .id(bookmark.getId())
        .name(bookmark.getName())
        .username(bookmark.getUserManager().getUsername())
        .email(bookmark.getUserManager().getEmail())
        .build();

    return dto;
  }

  private List<BookmarkResponseDto> generateBookmarkResponseDtoList(List<Bookmark> bookmarkList){
    List<BookmarkResponseDto> dtos = new ArrayList<>();

    for(Bookmark b : bookmarkList){
      BookmarkResponseDto dto = generateBookmarkResponseDto(b);
      dtos.add(dto);
    }

    return dtos;
  }

}
