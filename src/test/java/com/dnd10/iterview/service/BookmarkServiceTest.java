package com.dnd10.iterview.service;

import com.dnd10.iterview.repository.BookmarkRepository;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

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

  @AfterEach
  public void setUp() {
    questionRepository.deleteAll();
    userRepository.deleteAll();
    bookmarkRepository.deleteAll();
  }

  @Test
  @DisplayName("북마크 생성")
  void addBookmark(){

  }

}
