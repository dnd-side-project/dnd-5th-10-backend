package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.QuestionRequestDto;
import com.dnd10.iterview.dto.QuestionResponseDto;
import com.dnd10.iterview.dto.QuestionTagResponseDto;
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
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;
  private final UserRepository userRepository;
  private final TagRepository tagRepository;
  private final QuestionTagRepository questionTagRepository;

  @Override
  public QuestionResponseDto getQuestion(Long questionId){
    Question question = questionRepository.findById(questionId)
        .orElseThrow(() -> new IllegalArgumentException("해당 문제가 존재하지 않습니다."));

    return new QuestionResponseDto(question);
  }

  @Override
  public QuestionResponseDto addQuestion(Principal principal, QuestionRequestDto requestDto){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Question question = generateQuestion(user, requestDto);
    Question saved = questionRepository.save(question);

    return new QuestionResponseDto(saved);
  }

  @Override
  public List<QuestionResponseDto> getAllQuestions(Pageable pageable){
    Page<Question> questionPage = questionRepository.findAll(pageable);

    return mappingPageToDto(questionPage);
  }

  @Override
  public List<QuestionResponseDto> getSearchQuestions(List<String> tagList, String keyword, Pageable pageable){

    if(tagList.isEmpty() && keyword.isEmpty()){ // tag, keyword 다 없으면 그냥 전부 다.
      return getAllQuestions(pageable);
    }
    else {
      Page<Question> questionPage = questionRepository.findWithTags(tagList, keyword, pageable);

      return mappingPageToDto(questionPage);
    }
  }

  @Override
  public List<QuestionResponseDto> getMyAllQuestions(Principal principal, Pageable pageable){
    User user = userRepository.findUserByEmail(principal.getName())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

    Page<Question> questionPage = questionRepository.findAllByUserManager(user, pageable);

    return mappingPageToDto(questionPage);
  }

  @Override
  public List<QuestionResponseDto> getQuiz(){
    return null;
  }

  private Question generateQuestion(User user, QuestionRequestDto requestDto){
    Question question = Question.builder()
        .content(requestDto.getContent())
        .bookmarkCount(requestDto.getBookmarkCount())
        .userManager(user)
        // question entity에 리스트 객체 넣어서 안해도 될줄 알았는데 NPE 오류.. builder 관련 찾아보기
        .questionTagList(new ArrayList<>())
        .build();

    Question saved = questionRepository.save(question);
    generateTags(saved, requestDto.getTags());

    return saved;
  }

  private void generateTags(Question question, List<String> tags){

    if(tags.isEmpty()) return; // 태그 없으면 그냥 없는채로 생성

    for(String s : tags){
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

  private List<QuestionResponseDto> mappingPageToDto(Page<Question> questionPage){
    List<QuestionResponseDto> questionList = questionPage.stream().map(
        q -> new QuestionResponseDto(q)).collect(Collectors.toList());

    return questionList;
  }
}
