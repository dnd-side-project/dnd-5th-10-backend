package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.AnswerResponseDto;
import com.dnd10.iterview.dto.BookmarkDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.AnswerRepository;
import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

  private final BookmarkRepository bookmarkRepository;
  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;
  private final AnswerRepository answerRepository;

  @Override
  public BookmarkDto addBookmark(Principal principal, Long questionId) {
    
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 문제가 존재하지 않습니다."));

    if(bookmarkRepository.existsByUserManagerAndQuestion(user, question)){
      throw new IllegalArgumentException("이미 북마크 한 문제입니다.");
    }

    Bookmark bookmark = Bookmark.builder()
        .userManager(user)
        .question(question)
        .build();

    question.likeUp();
    Bookmark saved = bookmarkRepository.save(bookmark);

    return generateBookmarkDto(saved);
  }

  @Override
  public List<BookmarkDto> getBookmark(Principal principal, Pageable pageable) {
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Page<Bookmark> bookmarkPage = bookmarkRepository.findAllByUserManager(user, pageable);

    return mappingPageToDto(bookmarkPage);
  }

  @Override
  public void deleteBookmark(Principal principal, Long bookmarkId) {
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
        .orElseThrow(() -> new IllegalArgumentException("해당 북마크가 존재하지 않습니다."));

    bookmark.getQuestion().likeDown();
    bookmarkRepository.delete(bookmark);
  }

  private BookmarkDto generateBookmarkDto(Bookmark bookmark){
    BookmarkDto dto = new BookmarkDto(bookmark);
    Optional<Answer> answer = answerRepository.findTopByQuestionOrderByLikedDesc(bookmark.getQuestion());

    if(!answer.isEmpty()) {
      AnswerResponseDto answerDto = new AnswerResponseDto(answer.get());
      dto.getQuestion().setMostLikedAnswer(answerDto);
    }

    return dto;
  }

  private List<BookmarkDto> mappingPageToDto(Page<Bookmark> bookmarkPage){
    List<BookmarkDto> bookmarkList = bookmarkPage.stream().map(
        q -> generateBookmarkDto(q)).collect(Collectors.toList());

    return bookmarkList;
  }
}
