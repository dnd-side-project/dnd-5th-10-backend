package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkQuestionDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.dto.QuestionTagResponseDto;
import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.BookmarkQuestion;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.BookmarkQuestionRepository;
import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
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
  private final BookmarkRepository bookmarkRepository;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;

  @Override
  public BookmarkQuestionDto addBookmarkQuestion(Principal principal, Long questionId,
      Long bookmarkId) {

    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 문제가 존재하지 않습니다."));

    if(!bookmark.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    } // 다른 유저가 북마크 변경 못하도록.

    question.likeUp(); // bookmarkCount 올리기

    BookmarkQuestion bookmarkQuestion = BookmarkQuestion.builder()
        .bookmarkManager(bookmark)
        .questionManager(question)
        .build();

    BookmarkQuestion saved = bookmarkQuestionRepository.save(bookmarkQuestion);

    return generateBookmarkQuestionDto(saved);
  }

  @Override
  public List<BookmarkQuestionDto> getBookmarkQuestion(Principal principal, Long bookmarkId, Pageable pageable){

    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    if(!bookmark.getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    }

    Page<BookmarkQuestion> bookmarkQuestionPage = bookmarkQuestionRepository.findAllByBookmarkManager(bookmark, pageable);

    return mappingPageToDto(bookmarkQuestionPage);
  }

  @Override
  public void deleteBookmarkQuestion(Principal principal, Long bookmarkQuestionId) {
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    BookmarkQuestion bookmarkQuestion = bookmarkQuestionRepository.findById(bookmarkQuestionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크한 문제가 존재하지 않습니다."));

    if(!bookmarkQuestion.getBookmarkManager().getUserManager().getId().equals(user.getId())){
      throw new IllegalArgumentException("해당 북마크의 소유자가 아닙니다.");
    }

    bookmarkQuestion.getQuestionManager().likeDown(); // bookmarkCount 낮추기

    bookmarkQuestionRepository.delete(bookmarkQuestion);
  }

  private BookmarkQuestionDto generateBookmarkQuestionDto(BookmarkQuestion bookmarkQuestion){

    return BookmarkQuestionDto.builder()
        .id(bookmarkQuestion.getId())
        .bookmarkId(bookmarkQuestion.getBookmarkManager().getId())
        .question(generateQuestionResponseDto(bookmarkQuestion.getQuestionManager()))
        .build();
  }

  private QuestionResponseDto generateQuestionResponseDto(Question question){

    List<QuestionTagResponseDto> dtoList = new ArrayList<>();

    for(QuestionTag t : question.getQuestionTagList()){
      QuestionTagResponseDto dto = QuestionTagResponseDto.builder()
          .tagTitle(t.getTagManager().getName())
          .build();

      dtoList.add(dto);
    }

    return QuestionResponseDto.builder()
        .id(question.getId())
        .content(question.getContent())
        //.create_date(question.getCreate_date())
        .bookmarkCount(question.getBookmarkCount())
        .email(question.getUserManager().getEmail())
        .username(question.getUserManager().getUsername())
        .tagList(dtoList)
        .build();
  } // todo: 굳이 복붙 말고, 그냥 service 주입해서 쓸지 고민

  private List<BookmarkQuestionDto> mappingPageToDto(Page<BookmarkQuestion> bookmarkQuestionPage){
    List<BookmarkQuestionDto> dtoList = bookmarkQuestionPage.stream().map(
        bq -> generateBookmarkQuestionDto(bq)).collect(Collectors.toList());

    return dtoList;
  }
}
