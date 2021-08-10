package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkQuestionDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.entity.BookmarkFolder;
import com.dnd10.iterview.entity.BookmarkQuestion;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.BookmarkQuestionRepository;
import com.dnd10.iterview.repository.BookmarkFolderRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkQuestionServiceImpl implements BookmarkQuestionService {

  private final BookmarkQuestionRepository bookmarkQuestionRepository;
  private final BookmarkFolderRepository bookmarkFolderRepository;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  @Override
  public BookmarkQuestionDto addBookmarkQuestion(Principal principal, Long questionId,
      Long bookmarkId) {

    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 문제가 존재하지 않습니다."));

    if(!bookmarkFolder.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    } // 다른 유저가 북마크 변경 못하도록.

    question.likeUp(); // bookmarkCount 올리기

    BookmarkQuestion bookmarkQuestion = BookmarkQuestion.builder()
        .bookmarkFolderManager(bookmarkFolder)
        .questionManager(question)
        .build();

    BookmarkQuestion saved = bookmarkQuestionRepository.save(bookmarkQuestion);

    return new BookmarkQuestionDto(saved);
  }

  @Override
  public List<BookmarkQuestionDto> getBookmarkQuestion(Principal principal, Long bookmarkId, Pageable pageable){

    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkFolder bookmarkFolder = bookmarkFolderRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    if(!bookmarkFolder.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    }

    Page<BookmarkQuestion> bookmarkQuestionPage = bookmarkQuestionRepository.findAllByBookmarkFolderManager(
        bookmarkFolder, pageable);

    return mappingPageToDto(bookmarkQuestionPage);
  }

  @Override
  public void deleteBookmarkQuestion(Principal principal, Long bookmarkQuestionId) {
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkQuestion bookmarkQuestion = bookmarkQuestionRepository.findById(bookmarkQuestionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크한 문제가 존재하지 않습니다."));

    if(!bookmarkQuestion.getBookmarkFolderManager().getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    }

    bookmarkQuestion.getQuestionManager().likeDown(); // bookmarkCount 낮추기

    bookmarkQuestionRepository.delete(bookmarkQuestion);
  }

  private List<BookmarkQuestionDto> mappingPageToDto(Page<BookmarkQuestion> bookmarkQuestionPage){
    List<BookmarkQuestionDto> dtoList = bookmarkQuestionPage.stream().map(
        bq -> new BookmarkQuestionDto(bq)).collect(Collectors.toList());

    return dtoList;
  }
}
