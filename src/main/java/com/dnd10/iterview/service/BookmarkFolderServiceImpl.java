package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkFolderRequestDto;
import com.dnd10.iterview.dto.BookmarkFolderResponseDto;
import com.dnd10.iterview.entity.BookmarkFolder;
import com.dnd10.iterview.entity.BookmarkQuestion;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.BookmarkQuestionRepository;
import com.dnd10.iterview.repository.BookmarkFolderRepository;
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
public class BookmarkFolderServiceImpl implements BookmarkFolderService {

  private final BookmarkFolderRepository bookmarkFolderRepository;
  private final BookmarkQuestionRepository bookmarkQuestionRepository;
  private final UserRepository userRepository;

  @Override
  public BookmarkFolderResponseDto addBookmarkFolder(Principal principal, BookmarkFolderRequestDto bookmarkFolderRequestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    if(bookmarkFolderRepository.existsByNameAndUserManager(bookmarkFolderRequestDto.getName(), user)){
      throw new IllegalArgumentException("동일한 이름의 북마크가 이미 존재합니다.");
    } // 동일한 이름 북마크 생성 안되도록.

    BookmarkFolder bookmarkFolder = BookmarkFolder.builder()
        .name(bookmarkFolderRequestDto.getName())
        .userManager(user).build();

    BookmarkFolder saved = bookmarkFolderRepository.save(bookmarkFolder);

    return new BookmarkFolderResponseDto(saved);
  }

  @Override
  public BookmarkFolderResponseDto updateBookmarkFolder(Principal principal, Long bookmarkId, BookmarkFolderRequestDto bookmarkFolderRequestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    if(bookmarkFolderRepository.existsByNameAndUserManager(bookmarkFolderRequestDto.getName(), user)){
      throw new IllegalArgumentException("동일한 이름의 북마크가 이미 존재합니다.");
    } // 동일한 이름 북마크 생성 안되도록.

    if(!bookmarkFolder.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    } // 다른 유저가 북마크 변경 못하도록.

    bookmarkFolder.changeName(bookmarkFolderRequestDto.getName());

    return new BookmarkFolderResponseDto(bookmarkFolder);
  }

  @Override
  public List<BookmarkFolderResponseDto> getMyBookmarkFolder(Principal principal){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    List<BookmarkFolder> bookmarkFolderList = bookmarkFolderRepository.findAllByUserManager(user);

    return generateBookmarkFolderResponseDtoList(bookmarkFolderList);
  }

  @Override
  public void deleteBookmarkFolder(Principal principal, Long bookmarkId){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    if(!bookmarkFolder.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    } // 다른 유저가 북마크 변경 못하도록.

    List<BookmarkQuestion> bookmarkQuestionList = bookmarkQuestionRepository.findAllByBookmarkFolderManager(
        bookmarkFolder);

    for(BookmarkQuestion b : bookmarkQuestionList){
      b.getQuestionManager().likeDown();
      bookmarkQuestionRepository.delete(b);
    } // 북마크 폴더 내 북마크 한 문제 정보 삭제.

    bookmarkFolderRepository.delete(bookmarkFolder);
  }

  private List<BookmarkFolderResponseDto> generateBookmarkFolderResponseDtoList(List<BookmarkFolder> bookmarkFolderList){
    List<BookmarkFolderResponseDto> dtos = new ArrayList<>();

    for(BookmarkFolder b : bookmarkFolderList){
      BookmarkFolderResponseDto dto = new BookmarkFolderResponseDto(b);
      dtos.add(dto);
    }

    return dtos;
  }

}
