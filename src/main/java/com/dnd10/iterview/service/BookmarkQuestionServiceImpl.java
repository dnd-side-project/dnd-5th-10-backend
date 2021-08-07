package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.BookmarkQuestionResponseDto;
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
import lombok.RequiredArgsConstructor;
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
  public BookmarkQuestionResponseDto addBookmarkQuestion(Principal principal, Long questionId,
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

    BookmarkQuestion bookmarkQuestion = BookmarkQuestion.builder()
        .bookmarkManager(bookmark)
        .questionManager(question)
        .build();

    BookmarkQuestion saved = bookmarkQuestionRepository.save(bookmarkQuestion);

    return generateBookmarkQuestionResponseDto(saved);
  }

  private BookmarkQuestionResponseDto generateBookmarkQuestionResponseDto(BookmarkQuestion bookmarkQuestion){

    return BookmarkQuestionResponseDto.builder()
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
}
