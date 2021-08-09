package com.dnd10.iterview.repository;

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

public class QuestionSearchExtensionImpl extends QuerydslRepositorySupport implements
    QuestionSearchExtension {

  public QuestionSearchExtensionImpl() {
    super(Question.class);
  }

  @Override
  public Page<Question> findWithTags(List<String> tagList, String keyword, Pageable pageable) {
    BooleanBuilder builder = new BooleanBuilder();
    QQuestion question = QQuestion.question;

    if(!tagList.isEmpty()) {
      for (String t : tagList) {
        builder.or(question.questionTagList.any().tagManager.name.eq(t));
      }
    }

    if(!keyword.isEmpty()) {
      builder.and(question.content.contains(keyword));
    }

    JPQLQuery<Question> questionSearch = from(question).where(builder);
    JPQLQuery<Question> questionQuery = getQuerydsl().applyPagination(pageable, questionSearch);
    QueryResults<Question> result = questionQuery.fetchResults();

    return new PageImpl<>(result.getResults(), pageable, result.getTotal());
  }

  /*@Override
  public List<Question> findWithTagsRandom(List<String> tagList) {
    return null;
  }*/
}
