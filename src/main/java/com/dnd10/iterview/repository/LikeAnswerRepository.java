package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Answer;
import com.dnd10.iterview.entity.LikeAnswer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LikeAnswerRepository extends JpaRepository<LikeAnswer, Long> {

  Optional<List<LikeAnswer>> findAllByUserManager_Id(Long id);

  @Query("select a from Answer a left join LikeAnswer la on a.id = la.id where la.userManager.id = :userId")
  Optional<List<Answer>> findAllAnswersLiked(@Param("userId")Long userId);

}
