package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.QQuestion;
import com.dnd10.iterview.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QuestionSearchExtensionImpl extends QuerydslRepositorySupport implements
    QuestionSearchExtension {

/*
  @PersistenceContext // 영속성 객체를 자동으로 삽입
  private EntityManager em;*/

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
    /*return queryFactory
          .selectFrom(question)
          .where(builder)
          .orderBy(question.create_date.desc()) // 우선 최신순을 default로
          .fetch();*/

  }

  /*@Override
  public List<Question> findWithTagsRandom(List<String> tagList) {
    return null;
  }*/
}
