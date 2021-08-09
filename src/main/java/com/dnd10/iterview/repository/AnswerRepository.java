package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer,Long> {

  Page<Answer> findAll(Pageable pageable);
  Page<Answer> findAnswersByQuestion(Question question, Pageable pageable);

  Page<Answer> findAllByUser(User user, Pageable pageable);

  Optional<Answer> findTopByQuestionOrderByLikedDesc(Question question);
}
