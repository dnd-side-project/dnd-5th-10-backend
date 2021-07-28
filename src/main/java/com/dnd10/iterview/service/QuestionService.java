package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.dto.QuestionTagResponseDto;
import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import com.dnd10.iterview.entity.Tag;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.QuestionRepository;
import com.dnd10.iterview.repository.QuestionTagRepository;
import com.dnd10.iterview.repository.TagRepository;
import com.dnd10.iterview.repository.UserRepository;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuestionService {

  QuestionRepository questionRepository;
  UserRepository userRepository;
  TagRepository tagRepository;
  QuestionTagRepository questionTagRepository;

  public QuestionService(QuestionRepository questionRepository, UserRepository userRepository,
      TagRepository tagRepository, QuestionTagRepository questionTagRepository) {
    this.questionRepository = questionRepository;
    this.userRepository = userRepository;
    this.tagRepository = tagRepository;
    this.questionTagRepository = questionTagRepository;
  }

  public QuestionResponseDto getQuestion(Long questionId){
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 문제가 존재하지 않습니다."));

    return generateQuestionResponseDto(question);
  }

  public QuestionResponseDto addQuestion(Principal principal, QuestionRequestDto requestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Question question = generateQuestion(user, requestDto);
    Question saved = questionRepository.save(question);

    return generateQuestionResponseDto(saved);
  }

  private Question generateQuestion(User user, QuestionRequestDto requestDto){
    Question question = Question.builder()
        .content(requestDto.getContent())
        .bookmark_count(requestDto.getBookmark_count())
        .create_date(LocalDate.now())
        .userManager(user)
        .questionTagList(new ArrayList<>())
        .build();

    Question saved = questionRepository.save(question);
    generateTags(saved, requestDto.getTags());

    return saved;
  }

  private void generateTags(Question question, String tags){

    // todo: 어떤 구분자로 나눠서 줄지는 프론트와 이야기
    String[] tagSplit = tags.split("/");
    for(String s : tagSplit){
      // 태그 미리 일부만 지정하기로 했으니 이외의 태그는 입력 못하도록
      Tag tag = tagRepository.findByName(s)
          .orElseThrow(() -> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));

      QuestionTag questionTag = QuestionTag.builder()
          .question(question)
          .tagManager(tag)
          .build();

      QuestionTag saved = questionTagRepository.save(questionTag);
      question.addTag(saved); // 양방향 매핑
    }
  }

  private QuestionResponseDto generateQuestionResponseDto(Question question){
    UserDto userDto = new UserDto(question.getUserManager());

    List<QuestionTagResponseDto> dtoList = new ArrayList<>();
    for(QuestionTag t : question.getQuestionTagList()){
      QuestionTagResponseDto dto = QuestionTagResponseDto.builder()
          .tagTitle(t.getTagManager().getName())
          .build();

      dtoList.add(dto);
    }

    return QuestionResponseDto.builder()
        .content(question.getContent())
        .create_date(question.getCreate_date())
        .bookmark_count(question.getBookmark_count())
        .user(userDto)
        .tagList(dtoList)
        .build();
  }
}
