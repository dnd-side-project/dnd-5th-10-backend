package com.dnd10.iterview.repository;

import com.dnd10.iterview.entity.Bookmark;
import com.dnd10.iterview.entity.BookmarkQuestion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface BookmarkQuestionRepository extends JpaRepository<BookmarkQuestion, Long> {
  Page<BookmarkQuestion> findAllByBookmarkManager(Bookmark bookmark, Pageable pageable);
  List<BookmarkQuestion> findAllByBookmarkManager(Bookmark bookmark);
}
