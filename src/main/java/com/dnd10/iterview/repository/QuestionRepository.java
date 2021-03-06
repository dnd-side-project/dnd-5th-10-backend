package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Question;
import com.dnd10.iterview.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionSearchExtension {
  Optional<Question> findById(Long id);
  Page<Question> findAllByUserManager(User user, Pageable pageable);

}
