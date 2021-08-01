package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.QuestionTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface QuestionRepository extends JpaRepository<Question, Long> {
  Optional<Question> findById(Long id);

 // List<Question> findAllByQuestionTagListWithinOrderByCreate_dateDesc(List<QuestionTag> tagList);
  //List<Question> findAllByQuestionTagListContainingOrderByBookmark_countDesc(List<QuestionTag> tagList);
}
