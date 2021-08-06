package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.BookmarkQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookmarkQuestionRepository extends JpaRepository<BookmarkQuestion, Long> {

}
