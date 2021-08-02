package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.QQuestion;
import com.dnd10.iterview.entity.Question;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class QuestionSearchExtensionImpl extends QuerydslRepositorySupport implements
    QuestionSearchExtension {


  @PersistenceContext // 영속성 객체를 자동으로 삽입
  private EntityManager em;

  public QuestionSearchExtensionImpl() {
    super(Question.class);
  }

  @Override
  public List<Question> findWithTags(List<String> tagList, String sort) {
    BooleanBuilder builder = new BooleanBuilder();
    QQuestion question = QQuestion.question;

    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    for(String t : tagList){
      builder.and(question.questionTagList.any().tagManager.name.eq(t));
    }

    if(sort.equals("bookmark_count")){
      return queryFactory
          .selectFrom(question)
          .where(builder)
          .orderBy(question.bookmark_count.desc())
          .fetch();
    }
    else{
      return queryFactory
          .selectFrom(question)
          .where(builder)
          .orderBy(question.create_date.desc()) // 우선 최신순을 default로
          .fetch();
    }

  }

  /*@Override
  public List<Question> findWithTagsRandom(List<String> tagList) {
    return null;
  }*/
}
