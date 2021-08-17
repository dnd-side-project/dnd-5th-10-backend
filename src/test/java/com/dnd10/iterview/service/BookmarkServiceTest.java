package com.dnd10.iterview.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.dnd10.iterview.dto.BookmarkDto;
import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import com.sun.security.auth.UserPrincipal;
import java.security.Principal;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Pageable;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookmarkServiceTest {

  @Autowired
  private QuestionRepository questionRepository;
  @Autowired
  private BookmarkRepository bookmarkRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private BookmarkService bookmarkService;

  private String email = "test@gmail.com";

  @AfterEach
  public void setUp() {
    bookmarkRepository.deleteAll(); // delete 할 때 연관관계 순서대로 삭제할 것.
    questionRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  @DisplayName("북마크 생성")
  void addBookmark(){
    User user = signUp(email);
    Principal principal = new UserPrincipal(user.getEmail());

    Question question = makeQuestion(user.getEmail());

    BookmarkDto dto = bookmarkService.addBookmark(principal, question.getId());

    assertThat(questionRepository.findById(question.getId()).get().getBookmarkCount()).isEqualTo(1);
    assertThat(bookmarkRepository.existsById(dto.getId())).isTrue();
  }

  @Test
  @DisplayName("북마크 삭제")
  void deleteBookmark(){
    User user = signUp(email);
    Principal principal = new UserPrincipal(user.getEmail());

    Question question1 = makeQuestion(user.getEmail());
    Question question2 = makeQuestion(user.getEmail());

    Bookmark bookmark = makeBookmark(question1, user);
    makeBookmark(question2, user);

    bookmarkService.deleteBookmark(principal, bookmark.getId());

    assertThat(bookmarkRepository.existsById(bookmark.getId())).isFalse();
  }

  @Test
  @DisplayName("북마크 조회")
  void getBookmark(){
    User user1 = signUp(email);
    Principal principal1 = new UserPrincipal(user1.getEmail());

    Question question1 = makeQuestion(user1.getEmail());
    Question question2 = makeQuestion(user1.getEmail());

    makeBookmark(question1, user1);
    makeBookmark(question2, user1); // user 1

    User user = UserDto.builder().username("username")
        .provider(AuthProvider.google)
        .email("hello@gmail.com")
        .providerId("11")
        .build()
        .toEntity();

    User user2 = userRepository.save(user);
    Principal principal2 =  new UserPrincipal(user2.getEmail()); // user2

    makeBookmark(question1, user2);

    assertThat(bookmarkService.getBookmark(principal1, Pageable.ofSize(5)).size()).isEqualTo(2);
    assertThat(bookmarkService.getBookmark(principal2, Pageable.ofSize(5)).size()).isEqualTo(1);
  }

  private User signUp(String userEmail){
    String username = "lee";
    final String email = userEmail;
    User user = UserDto.builder().username(username)
        .provider(AuthProvider.google)
        .email(email)
        .providerId("11")
        .build()
        .toEntity();

    return userRepository.save(user);
  }

  private Question makeQuestion(String userEmail){
    final User user = userRepository.findUserByEmail(userEmail)
        .orElseThrow(IllegalArgumentException::new);
    Question question = Question.builder()
        .content("문제 테스트 입니다. 문제 테스트 입니다.")
        .userManager(user)
        .bookmarkCount(0L)
        .questionTagList(new ArrayList<>())
        .build();

    return questionRepository.save(question);
  }

  private Bookmark makeBookmark(Question question, User user){
    Bookmark bookmark = Bookmark.builder()
        .question(question)
        .userManager(user)
        .build();

    return bookmarkRepository.save(bookmark);
  }
}
