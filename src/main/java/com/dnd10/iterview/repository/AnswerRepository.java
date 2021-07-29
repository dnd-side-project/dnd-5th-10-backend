package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Answer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AnswerRepository extends JpaRepository<Answer,Long> {
  Optional<List<Answer>> findAllByQuestionManager_Id(Long id);
}
