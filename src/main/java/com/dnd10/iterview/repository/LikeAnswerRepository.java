package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.LikeAnswer;
import com.dnd10.iterview.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LikeAnswerRepository extends JpaRepository<LikeAnswer, Long> {

  Page<LikeAnswer> findAllByUserManager_Id(Long id, Pageable pageable);
  Optional<LikeAnswer> findByUserManager_IdAndAnswerManager_Id(Long userId, Long AnswerId);
  Optional<LikeAnswer> findByUserManagerAndAnswerManager(User user, Answer answer);
}
