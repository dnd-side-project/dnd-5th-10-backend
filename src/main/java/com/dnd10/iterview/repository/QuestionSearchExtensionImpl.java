package com.dnd10.iterview.repository;

import com.dnd10.iterview.dto.QuizRequestDto;
import com.dnd10.iterview.entity.QQuestion;
import com.dnd10.iterview.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionSearchExtensionImpl extends QuerydslRepositorySupport implements
    QuestionSearchExtension {

  public QuestionSearchExtensionImpl() {
    super(Question.class);

  }

  @Override
  public Page<Question> findWithTags(List<String> tagList, Pageable pageable) {
    BooleanBuilder builder = new BooleanBuilder();
    QQuestion question = QQuestion.question;

    for(String t : tagList){
      builder.and(question.questionTagList.any().tagManager.name.eq(t));
    }

    JPQLQuery<Question> questionSearch = from(question).where(builder);
    JPQLQuery<Question> questionQuery = getQuerydsl().applyPagination(pageable, questionSearch);
    QueryResults<Question> result = questionQuery.fetchResults();

    return new PageImpl<>(result.getResults(), pageable, result.getTotal());
  }

  @Override
  public List<Question> findWithTags(QuizRequestDto quizRequestDto) {

    BooleanBuilder builder = new BooleanBuilder();
    QQuestion question = QQuestion.question;

    for (String tag : quizRequestDto.getTags()) {
      builder.or(question.questionTagList.any().tagManager.name.eq(tag));
    }

    JPQLQuery<Question> questionSearch = from(question).where(builder);
    return questionSearch.fetch();
  }
  /*@Override
  public List<Question> findWithTagsRandom(List<String> tagList) {
    return null;
  }*/
}
